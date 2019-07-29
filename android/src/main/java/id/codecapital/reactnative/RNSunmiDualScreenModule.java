
package id.codecapital.reactnative;

import android.os.Environment;
import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableArray;

import id.codecapital.reactnative.utils.DataModel;
import id.codecapital.reactnative.utils.SharePreferenceUtil;
import id.codecapital.reactnative.utils.UPacketFactory;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import sunmi.ds.DSKernel;
import sunmi.ds.SF;
import sunmi.ds.callback.ICheckFileCallback;
import sunmi.ds.callback.IConnectionCallback;
import sunmi.ds.callback.IReceiveCallback;
import sunmi.ds.callback.ISendCallback;
import sunmi.ds.callback.ISendFilesCallback;
import sunmi.ds.callback.QueryCallback;
import sunmi.ds.data.DSData;
import sunmi.ds.data.DSFile;
import sunmi.ds.data.DSFiles;
import sunmi.ds.data.DataPacket;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;

public class RNSunmiDualScreenModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private DSKernel mDSKernel = null;
    private IConnectionCallback mIConnectionCallback;
    private IReceiveCallback mIReceiveCallback;
    private String secondScreenDeviceModel = null;

    public RNSunmiDualScreenModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }


    @Override
    public String getName() {
        return "RNSunmiDualScreen";
    }

    public void showMessage(String message) {
        Toast.makeText(RNSunmiDualScreenModule.this.reactContext, "" + message, Toast.LENGTH_SHORT).show();
    }


    @ReactMethod
    public void init() {
        mIConnectionCallback = new IConnectionCallback() {
            @Override
            public void onDisConnect() {
                RNSunmiDualScreenModule.this.showMessage("Device has been disconnected");
            }

            @Override
            public void onConnected(ConnState state) {
                String message = "";
                switch (state) {
                    case AIDL_CONN:
                        message = "与远程服务绑定成功";
                        break;

                    case VICE_SERVICE_CONN:
                        message = "与副屏服务通讯正常";
                        break;

                    case VICE_APP_CONN:
                        message = "与副屏app通讯正常";
                        break;

                    default:
                        break;
                }

                RNSunmiDualScreenModule.this.showMessage(message);
            }
        };

        mIReceiveCallback = new IReceiveCallback() {
            @Override
            public void onReceiveData(DSData data) {

            }

            @Override
            public void onReceiveFile(DSFile file) {

            }

            @Override
            public void onReceiveFiles(DSFiles files) {

            }

            @Override
            public void onReceiveCMD(DSData cmd) {

            }
        };

        // Create New mDS Kernel Instance
        mDSKernel = DSKernel.newInstance();
        mDSKernel.init(this.reactContext, mIConnectionCallback);
        mDSKernel.addReceiveCallback(mIReceiveCallback);


        // Get Second Screen Device Model
        JSONObject sentData = new JSONObject();
        try {
            sentData.put("dataModel", "GET_MODEL");
            sentData.put("data", "");
        } catch (JSONException e) {
            // errorCallback.invoke(e.getMessage());
        }

        DataPacket p2 = new DataPacket
                .Builder(DSData.DataType.CMD)
                .recPackName(SF.SUNMI_DSD_PACKNAME).data(sentData.toString())
                .addCallback(new ISendCallback() {
                    @Override
                    public void onSendSuccess(long taskId) {

                    }

                    @Override
                    public void onSendFail(int errorId, String errorInfo) {

                    }

                    @Override
                    public void onSendProcess(long totle, long sended) {

                    }
                }).build();

        mDSKernel.sendQuery(p2, new QueryCallback() {
            @Override
            public void onReceiveData(final DSData data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // RNSunmiDualScreenModule.this.showMessage("Model: " + data.data);
                        secondScreenDeviceModel = data.data;
                    }
                });
            }
        });
    }



    @ReactMethod
    public void getSecondScreenDeviceModel(Promise p) {
      p.resolve(secondScreenDeviceModel);
    }

    @ReactMethod
    public void getDeviceStoragePath(Promise p) {
        String storagePath = Environment.getExternalStorageDirectory().getPath();
        p.resolve(storagePath);
    }


    @ReactMethod
    public void showWelcomeScreen(final Callback successCallback, final Callback errorCallback) {
        JSONObject config = new JSONObject();
        try {
            config.put("dataModel", "SHOW_IMG_WELCOME");
            config.put("data", "");
        } catch (JSONException e) {
            errorCallback.invoke(e.getMessage());
        }

        mDSKernel.sendCMD(SF.DSD_PACKNAME, config.toString(), -1, null);
        successCallback.invoke();
    }


    @ReactMethod
    public void showTwoLineText(String title, String content, final Callback successCallback, final Callback errorCallback) {
        JSONObject config = new JSONObject();
        try {
            config.put("title", title);
            config.put("content", content);
        } catch (JSONException e) {
            errorCallback.invoke(e.getMessage());
        }

        DataPacket packet = UPacketFactory.buildShowText(DSKernel.getDSDPackageName(), config.toString(), new ISendCallback() {
            @Override
            public void onSendSuccess(long taskId) {

            }

            @Override
            public void onSendFail(int errorId, String errorInfo) {
                errorCallback.invoke(errorInfo);
            }

            @Override
            public void onSendProcess(long totle, long sended) {

            }
        });

        mDSKernel.sendData(packet);
        successCallback.invoke();
    }


    @ReactMethod
    public void showList(String data, final Callback successCallback, final Callback errorCallback) {
        JSONObject modelConfig = new JSONObject();
        try {
            modelConfig.put("data", data);
            modelConfig.put("dataModel", "TEXT");
        } catch (JSONException e) {
            errorCallback.invoke(e.getMessage());
        }

        JSONObject config = new JSONObject();
        try {
            config.put("data", modelConfig.toString());
            config.put("dataType", "DATA");
        } catch (JSONException e) {
            errorCallback.invoke(e.getMessage());
        }

        mDSKernel.TEST(config.toString());
        successCallback.invoke();


        /* try {
            JSONObject json = new JSONObject();

            JSONObject data = new JSONObject();
            data.put("title", "商米奶茶店收银");

            JSONObject head = new JSONObject();
            head.put("param1", "序号");
            head.put("param2", "商品名");
            head.put("param3", "单价");

            data.put("head", head);

            JSONArray list = new JSONArray();
            for (int i = 1; i < 11; i++) {
                JSONObject listItem = new JSONObject();
                listItem.put("param1", "" + i);
                listItem.put("param2", products.get(i - 1));
                listItem.put("param3", prices.get(i - 1));
                list.put(listItem);
            }
            data.put("list", list);

            JSONArray KVPList = new JSONArray();
            JSONObject KVPListOne = new JSONObject();
            KVPListOne.put("name", "总计 ");
            KVPListOne.put("value", "132.00");
            JSONObject KVPListTwo = new JSONObject();
            KVPListTwo.put("name", "优惠 ");
            KVPListTwo.put("value", "12.00");
            JSONObject KVPListThree = new JSONObject();
            KVPListThree.put("name", "数量 ");
            KVPListThree.put("value", "10");
            JSONObject KVPListFour = new JSONObject();
            KVPListFour.put("name", "应收 ");
            KVPListFour.put("value", "120.00");
            KVPList.put(0, KVPListOne);
            KVPList.put(1, KVPListTwo);
            KVPList.put(2, KVPListThree);
            KVPList.put(3, KVPListFour);

            data.put("KVPList", KVPList);


            json.put("data", data.toString());
            json.put("dataModel", "TEXT");
            JSONObject j = new JSONObject();
            j.put("data", json.toString());
            j.put("dataType", "DATA");

            Log.d("JSON STRING", j.toString());
            mDSKernel.TEST(j.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } */
    }


    @ReactMethod
    public void showSlidingImages(ReadableArray images, final Callback successCallback, final Callback errorCallback) {
        JSONObject config = new JSONObject();
        try {
            config.put("rotation_time",5000);
        } catch (JSONException e) {
            errorCallback.invoke(e.getMessage());
        }

        List imageList = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            imageList.add(images.getString(i));
        }

        mDSKernel.sendFiles(DSKernel.getDSDPackageName(), config.toString(), imageList, new ISendFilesCallback() {
            @Override
            public void onAllSendSuccess(long fileId) {
                showSentSlidingImages(fileId);
            }

            @Override
            public void onSendSuccess(String path, long taskId) {

            }

            @Override
            public void onSendFaile(int errorId, String errorInfo) {

            }

            @Override
            public void onSendFileFaile(String path, int errorId, String errorInfo) {

            }

            @Override
            public void onSendProcess(String path, long totle, long sended) {

            }
        });

        successCallback.invoke();
    }

    private void showSentSlidingImages(long fileId) {
        String json = UPacketFactory.createJson(DataModel.IMAGES,"");
        mDSKernel.sendCMD(DSKernel.getDSDPackageName(), json, fileId,null);
    }
}
