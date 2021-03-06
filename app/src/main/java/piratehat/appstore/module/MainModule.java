package piratehat.appstore.module;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import piratehat.appstore.Bean.BannerBean;
import piratehat.appstore.config.Constant;
import piratehat.appstore.config.Url;
import piratehat.appstore.contract.IMainContract;


import piratehat.appstore.utils.CacheUtil;

import piratehat.appstore.utils.JsoupUtil;
import piratehat.appstore.utils.OkHttpResultCallback;
import piratehat.appstore.utils.OkHttpUtil;

/**
 *
 * Created by PirateHat on 2018/10/27.
 */

public class MainModule implements IMainContract.IModel {
    private static final String TAG = "MainModule";

    @Override
    public void getMainPage(final IMainContract.IPresenter presenter) {
        List list ;
        if ((list = CacheUtil.getInstance().get(Url.MAIN_PAGE))!=null){
            presenter.setBanner((ArrayList<BannerBean>) list);
            return ;
        }

        Map<String, String> map = new HashMap<>();
        map.put(Constant.USER_AGENT, Constant.USER_AGENT_VALUE);
        OkHttpUtil.getInstance().getAsync(Url.MAIN_PAGE, new OkHttpResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                presenter.showError(e.getMessage());
            }

            @Override
            public void onResponse(String msg) {

               presenter.setBanner((ArrayList<BannerBean>) JsoupUtil.getInstance().getBanner(msg));
                CacheUtil.getInstance().put(Url.MAIN_PAGE,JsoupUtil.getInstance().getBanner(msg));
//               presenter.setRankApps((JsoupUtil.getInstance().getRankApps(msg)));
//               presenter.setBoutiqueApps(JsoupUtil.getInstance().getBoutiqueApps(msg));
//                DiskCacheManager.getDiskInstance().put(Url.MAIN_PAGE,msg);

            }

        }, map);
    }



}
