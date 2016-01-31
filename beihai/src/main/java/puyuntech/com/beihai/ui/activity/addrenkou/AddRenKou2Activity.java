package puyuntech.com.beihai.ui.activity.addrenkou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.nicodelee.utils.StringUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import puyuntech.com.beihai.R;
import puyuntech.com.beihai.app.AppDataUtils;
import puyuntech.com.beihai.app.BaseAct;
import puyuntech.com.beihai.model.RenKouModel;

/**
 *
 */
@ContentView(R.layout.activity_add_ren_kou2)
public class AddRenKou2Activity extends BaseAct {
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;//头部
    RenKouModel model = null;
    @ViewInject(R.id.shenfenzhenghao_tv)
    TextView shenfenzhenghao_tv;//身份证
    @ViewInject(R.id.name_tv)
    TextView name_tv;//真实姓名
    @ViewInject(R.id.pianqu_tv)
    TextView pianqu_tv;//片区
    @ViewInject(R.id.lianxifangshi_tv)
    TextView lianxifangshi_tv;//联系方式
    @ViewInject(R.id.minzu_tv)
    TextView minzu_tv;//民族
    @ViewInject(R.id.jiguan_tv)
    TextView jiguan_tv;//籍贯
    @ViewInject(R.id.xianju_tv)
    TextView xianju_tv;//现居住
    @ViewInject(R.id.beizhu_tv)
    TextView beizhu_tv;//备注
    @ViewInject(R.id.zhengzhi_tv)
    TextView zhengzhi_tv;//政治
    @ViewInject(R.id.wenhua_tv)
    TextView wenhua_tv;//文化
    @ViewInject(R.id.hunyin_tv)
    TextView hunyin_tv;//婚姻
    @ViewInject(R.id.zongjiao_tv)
    TextView zongjiao_tv;//宗教

    /**
     * 提交
     *
     * @param view
     */
    @Event({R.id.ok_tv})
    private void clickEvent(final View view) {
        switch (view.getId()) {
            case R.id.ok_tv:
                submitInfo();
                break;

        }
    }

    /**
     * 提交信息
     */
    private void submitInfo() {
        if (model == null) {
            showShortToast("model cannot to be null");
            return;
        }
        if (StringUtils.isEmpty(model.cardNum)) {//身份证号码
            shenfenzhenghao_tv.setError(getString(R.string.null_inster));
            return;
        }
        if (StringUtils.isEmpty(model.name)) {//真实姓名
            name_tv.setError(getString(R.string.null_inster));
            return;
        }
        if (StringUtils.isEmpty(model.sspq)) {//所属片区
            pianqu_tv.setError(getString(R.string.null_inster));
            return;
        }
        if (StringUtils.isEmpty(model.lxfs)) {//联系方式
            lianxifangshi_tv.setError(getString(R.string.null_inster));
            return;
        }
        if (StringUtils.isEmpty(model.mz)) {//民族
            minzu_tv.setError(getString(R.string.null_inster));
            return;
        }
        if (StringUtils.isEmpty(model.jg)) {//籍贯
            jiguan_tv.setError(getString(R.string.null_inster));
            return;
        }
        if (StringUtils.isEmpty(model.xjdd)) {//现居地址
            xianju_tv.setError(getString(R.string.null_inster));
            return;
        }
        if (StringUtils.isEmpty(model.bzh)) {//备注
            beizhu_tv.setError(getString(R.string.null_inster));
            return;
        }
        if (StringUtils.isEmpty(model.zhzhmm)) {//政治面貌
            zhengzhi_tv.setError(getString(R.string.null_inster));
            return;
        }
        if (StringUtils.isEmpty(model.whchd)) {//文化程度
            wenhua_tv.setError(getString(R.string.null_inster));
            return;
        }
        if (StringUtils.isEmpty(model.hyzhk)) {//婚姻状况
            hunyin_tv.setError(getString(R.string.null_inster));
            return;
        }
        if (StringUtils.isEmpty(model.zjxy)) {//宗教信仰
            zongjiao_tv.setError(getString(R.string.null_inster));
            return;
        }
        showShortToast("录入成功");
        //通知上级页面关闭
        finishOK();
    }


    /**
     * 输入
     *
     * @param view
     */
    @Event({R.id.lianxifangshi_tv, R.id.minzu_tv, R.id.jiguan_tv, R.id.xianju_tv, R.id.beizhu_tv})
    private void insertInfoEvent(final View view) {
        switch (view.getId()) {
            case R.id.lianxifangshi_tv:
                showEdDialog("请输入联系方式", model.lxfs, EditorInfo.TYPE_CLASS_PHONE, 0, new AfterInsert() {
                    @Override
                    public void after(String text) {
                        model.lxfs = text;
                        ((TextView) view).setText(text);
                        ((TextView) view).setError(null);

                    }
                });
                break;
            case R.id.minzu_tv:
                showEdDialog("请输入名族", model.mz, EditorInfo.TYPE_CLASS_TEXT, 1, new AfterInsert() {
                    @Override
                    public void after(String text) {
                        model.mz = text;
                        ((TextView) view).setText(text);
                        ((TextView) view).setError(null);

                    }
                });
                break;
            case R.id.jiguan_tv:
                showEdDialog("请输入籍贯", model.jg, EditorInfo.TYPE_CLASS_TEXT, -1, new AfterInsert() {
                    @Override
                    public void after(String text) {
                        model.jg = text;
                        ((TextView) view).setText(text);
                        ((TextView) view).setError(null);

                    }
                });
                break;
            case R.id.xianju_tv:
                showEdDialog("请输入现居地址", model.xjdd, EditorInfo.TYPE_CLASS_TEXT, -1, new AfterInsert() {
                    @Override
                    public void after(String text) {
                        model.xjdd = text;
                        ((TextView) view).setText(text);
                        ((TextView) view).setError(null);

                    }
                });
                break;
            case R.id.beizhu_tv:
                showEdDialog("请输入备注", model.bzh, EditorInfo.TYPE_CLASS_TEXT, -1, new AfterInsert() {
                    @Override
                    public void after(String text) {
                        model.bzh = text;
                        ((TextView) view).setText(text);
                        ((TextView) view).setError(null);

                    }
                });
                break;
        }


    }


    /**
     * 选择
     *
     * @param view
     */
    @Event({R.id.zhengzhi_tv, R.id.wenhua_tv, R.id.hunyin_tv, R.id.zongjiao_tv})
    private void selectInfoEvent(final View view) {
        switch (view.getId()) {
            case R.id.zhengzhi_tv:
                showSLDialog("请选择政治面貌", AppDataUtils.ZHENG_ZHI_MIAN_MAO, new AfterSelect() {
                    @Override
                    public void after(int position,String text) {
                        model.zhzhmm = text;
                        ((TextView) view).setText(text);
                    }
                });
                break;
            case R.id.wenhua_tv:
                showSLDialog("请选择文化程度", AppDataUtils.WEN_HUA_CHENG_DU, new AfterSelect() {
                    @Override
                    public void after(int position,String text) {
                        model.whchd = text;
                        ((TextView) view).setText(text);
                    }
                });
                break;
            case R.id.hunyin_tv:
                showSLDialog("请选择婚姻状况", AppDataUtils.HUN_YIN_ZHUANG_KUANG, new AfterSelect() {
                    @Override
                    public void after(int position,String text) {
                        model.hyzhk = text;
                        ((TextView) view).setText(text);
                    }
                });
                break;
            case R.id.zongjiao_tv:
                showSLDialog("请选择宗教信仰", AppDataUtils.ZONG_JIAO_XING_YANG, new AfterSelect() {
                    @Override
                    public void after(int position,String text) {
                        model.zjxy = text;
                        ((TextView) view).setText(text);
                    }
                });
                break;

        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitle();
    }

    /**
     * 接收传过来的人口对象
     *
     * @param event
     */
    public void onEvent(RenKouModel event) {
        model = event;
        shenfenzhenghao_tv.setText(model.cardNum);//身份证
        name_tv.setText(model.name);//真实姓名
        pianqu_tv.setText(model.sspq);//所属片区
        showShortToast("cardnum:" + model.cardNum + "name:" + model.name);
    }

    /**
     * //初始化头部
     */
    private void showTitle() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("人口登记");
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_return);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //返回建设置事件
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
