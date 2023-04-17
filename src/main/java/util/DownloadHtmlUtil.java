package util;
import com.alibaba.fastjson.JSONArray;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSONObject;




public class DownloadHtmlUtil {
    public List<Map<String,Object>> DownloadHtml(String url) throws ParserConfigurationException, XPathExpressionException, IOException, InterruptedException {
        List<Map<String,Object>> rl = new ArrayList<Map<String,Object>>();
        JSONObject jsonObject = getJsonObject(url);
        JSONArray items = jsonObject.getJSONObject("data").getJSONArray("item");
        for (int i = 0; i < items.size(); i++) {
            String item = items.getString(i);
            JSONObject json_item = JSONObject.parseObject(item);
            String school_id = json_item.getString("school_id");
            String belong = json_item.getString("belong");
            String city_name = json_item.getString("city_name");
            String name = json_item.getString("name");
            String level_name = json_item.getString("level_name");
            String nature_name = json_item.getString("nature_name");
            String province_name = json_item.getString("province_name");
            String type_name = json_item.getString("type_name");
            String dual_class_name = json_item.getString("dual_class_name");
            if (dual_class_name.length()==0){
                dual_class_name = null;
            }
            JSONObject school_info = getJsonObject(Url.SCHOOL_INFO_URL.getUrl(school_id)).getJSONObject("data");
            String address = school_info.getString("address");
            String school_site = school_info.getString("school_site");
            String content = school_info.getString("content");
            getSchoolMajor(Url.SCHOOL_MAJOR_URL.getUrl(school_id));
            getSchoolScore(Url.SCHOOL_SCORE_URL.getUrl(school_id));



        }




        return rl;
    }

    private void getSchoolScore(String url) throws IOException {
        JSONObject school_score = getJsonObject(url);
        JSONArray items = school_score.getJSONObject("data").getJSONArray("item");
        for (int i = 0; i < items.size(); i++) {
            String item = items.getString(i);
            JSONObject school_score_item = JSONObject.parseObject(item);
            // todo
        }
    }

    private void getSchoolMajor(String school_major_url) throws IOException {
        List<String> major = new ArrayList<String>();
        JSONObject school_major_data = getJsonObject(school_major_url);
        JSONArray school_major_items = school_major_data.getJSONObject("data")
                .getJSONObject("special_detail").
                getJSONArray("2");
        for (int j = 0 ; j < school_major_items.size(); j++){
            String temp_major = school_major_items.getString(j);
            JSONObject school_major_item = JSONObject.parseObject(temp_major);
            String special_name = school_major_item.getString("special_name");
            String limit_year = school_major_item.getString("limit_year");
            String type_name = school_major_item.getString("type_name");
            String level2_name = school_major_item.getString("level2_name");
            // todo return type


        }


    }

    private JSONObject getJsonObject(String url) throws IOException {
        Connection.Response res = Jsoup.connect(url)
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Accept-Language","zh-CN,zh;q=0.9")
                .header("Content-Type", "application/json;charset=UTF-8")
                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36")
                .header("Cookie","Hm_lvt_0a962de82782bad64c32994d6c9b06d3=1681049933; gr_user_id=a762e1b4-6095-4638-9b52-b40b0a4a1194")
                .timeout(10000).ignoreContentType(true).execute();

        String body = res.body();
        JSONObject jsonObject = JSONObject.parseObject(body);
        System.out.println(jsonObject.getString("message") + ":" +url);
        return jsonObject;
    }

}
