package com.example.notreaddit;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.notreaddit.MainActivity.ip;

public class ResultsActivity extends AppCompatActivity {

    EditText searchEditText;
    ImageButton searchButton;
    ProgressBar progressBar;
    ViewPager articleListView;
    ArticlesAdapter articleAdapter;
    LinearLayout radar;
    LinkedHashSet<String> uniqueNodes;
    HashMap<String,Point> nodeMap;
    Article[] articles;

    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        searchButton = findViewById(R.id.results_search_btn);
        searchEditText = findViewById(R.id.results_search_text);
        progressBar = findViewById(R.id.results_progress);
        radar = findViewById(R.id.radar);

        articleListView = findViewById(R.id.results_articles_layout);

        if(getIntent().hasExtra("query")){
            query = (getIntent().getStringExtra("query"));
            searchEditText.setText(query);
            radar.post(new Runnable() {
                @Override
                public void run() {
                    request(query);
                    //handleResponse("{\"result\": \"success\", \"lemmatizedQuery\": \"object\", \"data\": [{\"id\": 637, \"title\": \"We strongly object to Pak's vulgar display of injured IAF pilot: Govt\", \"content\": \"India on Wednesday summoned Pakistan's Acting High Commissioner and strongly objected to Pakistan's \\\"vulgar display\\\" of injured personnel of the Indian Air Force. India called Pakistan's act \\\"violation of all norms of International Humanitarian Law and the Geneva Convention.\\\" \\\"India expects his immediate and safe return,\\\" the Ministry of External Affairs said in the Ministry of External Affairs statement.\", \"imageUrl\": \"https://assets.inshorts.com/inshorts/images/v1/variants/jpg/m/2019/02_feb/27_wed/img_1551275947032_425.jpg?resize=400px:*\", \"readMoreUrl\": \"https://twitter.com/ANI/status/1100747033269415936?utm_campaign=fullarticle&utm_medium=referral&utm_source=inshorts \", \"timestamp\": 1551309720, \"trees\": [\"(S\\n (NNNP india/NNP)\\n on/IN\\n (NNNP wednesday/NNP)\\n (VVVV summon/VBD)\\n (NNNP pakistan/NNP acting/NNP high/NNP commissioner/NNP)\\n (VVVV object/VBD)\\n (NNNP pakistan/NNP)\\n (NIN (NNNN display/NN) of/IN (NNNN personnel/NNS))\\n of/IN\\n (NNNP indian/NNP air/NNP force/NNP))\", \"(S\\n (NNNP india/NNP)\\n (VVVV call/VBD)\\n (NNNP pakistan/NNP)\\n (NIN (NNNN act/NN violation/NN) of/IN (NNNN norm/NNS))\\n of/IN\\n (NNNP international/NNP humanitarian/NNP law/NNP geneva/NNP)\\n (NNNN convention/NN))\", \"(S\\n (NNNP india/NNP)\\n (VVVV expect/VBZ)\\n (NNNN immediate/NN return/NN)\\n (NNNP ministry/NNP)\\n of/IN\\n (NNNP external/NNP)\\n affair/NNPS\\n (VVVV say/VBD)\\n in/IN\\n (NNNP ministry/NNP)\\n of/IN\\n (NNNP external/NNP affair/NNP)\\n (NNNN statement/NN))\"], \"tokens\": [[\"india\", \"NNP\"], [\"on\", \"IN\"], [\"wednesday\", \"NNP\"], [\"summon\", \"VBD\"], [\"pakistan\", \"NNP\"], [\"acting\", \"NNP\"], [\"high\", \"NNP\"], [\"commissioner\", \"NNP\"], [\"and\", \"CC\"], [\"strongly\", \"RB\"], [\"object\", \"VBD\"], [\"to\", \"TO\"], [\"pakistan\", \"NNP\"], [\"vulgar\", \"FW\"], [\"display\", \"NN\"], [\"of\", \"IN\"], [\"injured\", \"JJ\"], [\"personnel\", \"NNS\"], [\"of\", \"IN\"], [\"indian\", \"NNP\"], [\"air\", \"NNP\"], [\"force\", \"NNP\"], [\"india\", \"NNP\"], [\"call\", \"VBD\"], [\"pakistan\", \"NNP\"], [\"act\", \"NN\"], [\"violation\", \"NN\"], [\"of\", \"IN\"], [\"norm\", \"NNS\"], [\"of\", \"IN\"], [\"international\", \"NNP\"], [\"humanitarian\", \"NNP\"], [\"law\", \"NNP\"], [\"and\", \"CC\"], [\"geneva\", \"NNP\"], [\"convention\", \"NNP\"], [\"india\", \"NNP\"], [\"expect\", \"VBZ\"], [\"his\", \"PRP$\"], [\"immediate\", \"NN\"], [\"and\", \"CC\"], [\"safe\", \"JJ\"], [\"return\", \"NN\"], [\"ministry\", \"NNP\"], [\"of\", \"IN\"], [\"external\", \"NNP\"], [\"affair\", \"NNPS\"], [\"say\", \"VBD\"], [\"in\", \"IN\"], [\"ministry\", \"NNP\"], [\"of\", \"IN\"], [\"external\", \"NNP\"], [\"affair\", \"NNPS\"], [\"statement\", \"NN\"]]}, {\"id\": 639, \"title\": \"What does the Geneva Convention say about Prisoners of War?\", \"content\": \"India on Wednesday objected to Pakistan's \\\"vulgar display\\\" of the injured IAF personnel in Pakistan's \\\"vulgar display\\\" of the injured IAF personnel in their custody custody, terming India a violation of the Geneva Convention. the Geneva Convention states a Prisoner of War (POW) cannot be physically or mentally tortured and must be provided with food, clothing, and medical care. Further, a POW must be released once hostilities between both sides end.\", \"imageUrl\": \"https://assets.inshorts.com/inshorts/images/v1/variants/jpg/m/2019/02_feb/27_wed/img_1551281387856_468.jpg?resize=400px:*\", \"readMoreUrl\": \"https://www.thenewsminute.com/article/explainer-what-geneva-convention-says-about-treatment-prisoners-war-97466?utm_campaign=fullarticle&utm_medium=referral&utm_source=inshorts \", \"timestamp\": 1551316860, \"trees\": [\"(S\\n (NNNP india/NNP)\\n on/IN\\n (NNNP wednesday/NNP)\\n (VVVV object/VBD)\\n (NNNP pakistan/NNP)\\n (NIN (NNNN display/NN) of/IN (NNNP iaf/NNP))\\n (NIN (NNNN personnel/NNS) in/IN (NNNP pakistan/NNP))\\n (NIN (NNNN display/NN) of/IN (NNNP iaf/NNP))\\n (NIN (NNNN personnel/NNS) in/IN (NNNN custody/NN custody/NN))\\n (VVVV term/VBG)\\n (NNNP india/NNP)\\n (NIN (NNNN violation/NN) of/IN (NNNP geneva/NNP))\\n (NNNN convention/NN))\", \"(S\\n (NNNP geneva/NNP convention/NNP)\\n (VVVV state/VBZ)\\n (NNNP prisoner/NNP)\\n of/IN\\n (NNNP war/NNP pow/NNP)\\n (VVVV be/VB be/VB provide/VBN)\\n with/IN\\n (NNNN food/NN clothing/NN care/NN))\", \"(S\\n (NNNP pow/NNP)\\n (VVVV be/VB release/VBN)\\n (NIN (NNNN hostility/NNS) between/IN (NNNN side/NNS))\\n (VVVV end/VBP))\"], \"tokens\": [[\"india\", \"NNP\"], [\"on\", \"IN\"], [\"wednesday\", \"NNP\"], [\"object\", \"VBD\"], [\"to\", \"TO\"], [\"pakistan\", \"NNP\"], [\"vulgar\", \"FW\"], [\"display\", \"NN\"], [\"of\", \"IN\"], [\"injured\", \"JJ\"], [\"iaf\", \"NNP\"], [\"personnel\", \"NNS\"], [\"in\", \"IN\"], [\"pakistan\", \"NNP\"], [\"vulgar\", \"FW\"], [\"display\", \"NN\"], [\"of\", \"IN\"], [\"injured\", \"JJ\"], [\"iaf\", \"NNP\"], [\"personnel\", \"NNS\"], [\"in\", \"IN\"], [\"their\", \"PRP$\"], [\"custody\", \"NN\"], [\"custody\", \"NN\"], [\"term\", \"VBG\"], [\"india\", \"NNP\"], [\"violation\", \"NN\"], [\"of\", \"IN\"], [\"geneva\", \"NNP\"], [\"convention\", \"NNP\"], [\"geneva\", \"NNP\"], [\"convention\", \"NNP\"], [\"state\", \"VBZ\"], [\"prisoner\", \"NNP\"], [\"of\", \"IN\"], [\"war\", \"NNP\"], [\"pow\", \"NNP\"], [\"can\", \"MD\"], [\"not\", \"RB\"], [\"be\", \"VB\"], [\"physically\", \"RB\"], [\"or\", \"CC\"], [\"mentally\", \"RB\"], [\"tortured\", \"JJ\"], [\"and\", \"CC\"], [\"must\", \"MD\"], [\"be\", \"VB\"], [\"provide\", \"VBN\"], [\"with\", \"IN\"], [\"food\", \"NN\"], [\"clothing\", \"NN\"], [\"and\", \"CC\"], [\"medical\", \"JJ\"], [\"care\", \"NN\"], [\"further\", \"NNP\"], [\"pow\", \"NNP\"], [\"must\", \"MD\"], [\"be\", \"VB\"], [\"release\", \"VBN\"], [\"once\", \"RB\"], [\"hostility\", \"NNS\"], [\"between\", \"IN\"], [\"side\", \"NNS\"], [\"end\", \"VBP\"]]}, {\"id\": 70, \"title\": \"Tracking 250 debris objects from ASAT test, ISS not at risk: US\", \"content\": \"The US is tracking 250-270 objects of space debris generated due to India's anti-satellite (ASAT) missile test in lower Earth orbit, but the International Space Station is not at risk, the Pentagon said on Friday. The US defence department added The US defence department will continue to track the debris and issue close approach notifications as required until The US defence department enters the Earth's atmosphere.\", \"imageUrl\": \"https://assets.inshorts.com/inshorts/images/v1/variants/jpg/m/2019/03_mar/30_sat/img_1553946788775_602.jpg?resize=400px:*\", \"readMoreUrl\": \"http://www.newindianexpress.com/world/2019/mar/30/us-tracking-250-270-objects-from-indian-asat-test-debris-iss-not-at-risk-pentagon-1957942.html?utm_campaign=fullarticle&utm_medium=referral&utm_source=inshorts \", \"timestamp\": 1553982900, \"trees\": [\"(S\\n (NNNP u/NNP)\\n (VVVV be/VBZ track/VBG)\\n (NIN (NNNN object/NNS) of/IN (NNNN space/NN debris/NN))\\n (VVVV generate/VBD)\\n (NNNP india/NNP asat/NNP)\\n (NIN (NNNN missile/NN test/NN) in/IN (NNNN earth/NN orbit/NN))\\n (NNNP international/NNP space/NNP station/NNP)\\n (VVVV be/VBZ)\\n at/IN\\n (NNNN risk/NN)\\n (NNNP pentagon/NNP)\\n (VVVV say/VBD)\\n on/IN\\n (NNNP friday/NNP))\", \"(S\\n (NNNP u/NNP)\\n (NNNN defence/NN department/NN)\\n (VVVV add/VBD)\\n (NNNP u/NNP)\\n (NNNN defence/NN department/NN)\\n (VVVV continue/VB track/VB)\\n (NNNN debris/NN issue/NN notification/NNS)\\n (NVIN a/IN (VVVV require/VBN) until/IN (NNNP u/NNP))\\n (NNNN defence/NN department/NN)\\n (VVVV enter/VBZ)\\n (NNNP earth/NNP)\\n (NNNN atmosphere/NN))\"], \"tokens\": [[\"u\", \"NNP\"], [\"be\", \"VBZ\"], [\"track\", \"VBG\"], [\"250-270\", \"JJ\"], [\"object\", \"NNS\"], [\"of\", \"IN\"], [\"space\", \"NN\"], [\"debris\", \"NN\"], [\"generate\", \"VBD\"], [\"due\", \"JJ\"], [\"to\", \"TO\"], [\"india\", \"NNP\"], [\"anti-satellite\", \"JJ\"], [\"asat\", \"NNP\"], [\"missile\", \"NN\"], [\"test\", \"NN\"], [\"in\", \"IN\"], [\"low\", \"JJR\"], [\"earth\", \"NN\"], [\"orbit\", \"NN\"], [\"but\", \"CC\"], [\"international\", \"NNP\"], [\"space\", \"NNP\"], [\"station\", \"NNP\"], [\"be\", \"VBZ\"], [\"not\", \"RB\"], [\"at\", \"IN\"], [\"risk\", \"NN\"], [\"pentagon\", \"NNP\"], [\"say\", \"VBD\"], [\"on\", \"IN\"], [\"friday\", \"NNP\"], [\"u\", \"NNP\"], [\"defence\", \"NN\"], [\"department\", \"NN\"], [\"add\", \"VBD\"], [\"u\", \"NNP\"], [\"defence\", \"NN\"], [\"department\", \"NN\"], [\"will\", \"MD\"], [\"continue\", \"VB\"], [\"to\", \"TO\"], [\"track\", \"VB\"], [\"debris\", \"NN\"], [\"and\", \"CC\"], [\"issue\", \"NN\"], [\"close\", \"RB\"], [\"approach\", \"JJ\"], [\"notification\", \"NNS\"], [\"a\", \"IN\"], [\"require\", \"VBN\"], [\"until\", \"IN\"], [\"u\", \"NNP\"], [\"defence\", \"NN\"], [\"department\", \"NN\"], [\"enter\", \"VBZ\"], [\"earth\", \"NNP\"], [\"atmosphere\", \"NN\"]]}, {\"id\": 487, \"title\": \"WikiLeaks source Chelsea Manning jailed for refusing to testify\", \"content\": \"Former US intelligence analyst Chelsea Manning has been jailed for refusing to testify before an investigation into WikiLeaks. Manning served seven years of a 35-year sentence for leaking secret military documents to WikiLeaks, with her sentence being commuted by former US President Barack Obama. Former US intelligence analyst Chelsea Manning has refused to testify as her objects to the secrecy of the grand jury process.\", \"imageUrl\": \"https://assets.inshorts.com/inshorts/images/v1/variants/jpg/m/2019/03_mar/9_sat/img_1552137329439_256.jpg?resize=400px:*\", \"readMoreUrl\": \"https://www.theguardian.com/us-news/2019/mar/08/chelsea-manning-judge-jails-wikileaks-case?utm_campaign=fullarticle&utm_medium=referral&utm_source=inshorts \", \"timestamp\": 1552175880, \"trees\": [\"(S\\n (NNNP former/NNP u/NNP)\\n (NNNN intelligence/NN analyst/NN)\\n (NNNP chelsea/NNP manning/NNP)\\n (VVVV have/VBZ be/VBN jail/VBN)\\n (NIN\\n (NVIN\\n for/IN\\n (VVVV refuse/VBG testify/VB)\\n before/IN\\n (NNNN investigation/NN))\\n into/IN\\n (NNNP wikileaks/NNP)))\", \"(S\\n (VVVV man/VBG serve/VBD)\\n (NIN (NNNN year/NNS) of/IN (NNNN sentence/NN))\\n for/IN\\n (VVVV leak/VBG)\\n (NNNN document/NNS)\\n (NNNP wikileaks/NNP)\\n with/IN\\n (NVIN\\n (NNNN sentence/NN)\\n (VVVV be/VBG commute/VBN)\\n by/IN\\n (NNNP u/NNP president/NNP barack/NNP obama/NNP)))\", \"(S\\n (NNNP former/NNP u/NNP)\\n (NNNN intelligence/NN analyst/NN)\\n (NNNP chelsea/NNP manning/NNP)\\n (VVVV have/VBZ refuse/VBN testify/VB)\\n a/IN\\n (NIN (NNNN object/NNS secrecy/NN) of/IN (NNNN jury/NN process/NN)))\"], \"tokens\": [[\"former\", \"NNP\"], [\"u\", \"NNP\"], [\"intelligence\", \"NN\"], [\"analyst\", \"NN\"], [\"chelsea\", \"NNP\"], [\"manning\", \"NNP\"], [\"have\", \"VBZ\"], [\"be\", \"VBN\"], [\"jail\", \"VBN\"], [\"for\", \"IN\"], [\"refuse\", \"VBG\"], [\"to\", \"TO\"], [\"testify\", \"VB\"], [\"before\", \"IN\"], [\"investigation\", \"NN\"], [\"into\", \"IN\"], [\"wikileaks\", \"NNP\"], [\"man\", \"VBG\"], [\"serve\", \"VBD\"], [\"seven\", \"CD\"], [\"year\", \"NNS\"], [\"of\", \"IN\"], [\"35-year\", \"JJ\"], [\"sentence\", \"NN\"], [\"for\", \"IN\"], [\"leak\", \"VBG\"], [\"secret\", \"JJ\"], [\"military\", \"JJ\"], [\"document\", \"NNS\"], [\"to\", \"TO\"], [\"wikileaks\", \"NNP\"], [\"with\", \"IN\"], [\"her\", \"PRP$\"], [\"sentence\", \"NN\"], [\"be\", \"VBG\"], [\"commute\", \"VBN\"], [\"by\", \"IN\"], [\"former\", \"JJ\"], [\"u\", \"NNP\"], [\"president\", \"NNP\"], [\"barack\", \"NNP\"], [\"obama\", \"NNP\"], [\"former\", \"NNP\"], [\"u\", \"NNP\"], [\"intelligence\", \"NN\"], [\"analyst\", \"NN\"], [\"chelsea\", \"NNP\"], [\"manning\", \"NNP\"], [\"have\", \"VBZ\"], [\"refuse\", \"VBN\"], [\"to\", \"TO\"], [\"testify\", \"VB\"], [\"a\", \"IN\"], [\"her\", \"PRP$\"], [\"object\", \"NNS\"], [\"to\", \"TO\"], [\"secrecy\", \"NN\"], [\"of\", \"IN\"], [\"grand\", \"JJ\"], [\"jury\", \"NN\"], [\"process\", \"NN\"]]}]}");
                }
            });
        }

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchButtonClicked();
            }
        });
    }

    private void searchButtonClicked(){
        query = String.valueOf(searchEditText.getText());
        if (TextUtils.isEmpty(query)){
            searchEditText.setError("Enter a search query!");
            return;
        }

        radar.removeAllViewsInLayout();

        request(query);
        //handleResponse("{\"result\": \"success\", \"lemmatizedQuery\": \"object\", \"data\": [{\"id\": 637, \"title\": \"We strongly object to Pak's vulgar display of injured IAF pilot: Govt\", \"content\": \"India on Wednesday summoned Pakistan's Acting High Commissioner and strongly objected to Pakistan's \\\"vulgar display\\\" of injured personnel of the Indian Air Force. India called Pakistan's act \\\"violation of all norms of International Humanitarian Law and the Geneva Convention.\\\" \\\"India expects his immediate and safe return,\\\" the Ministry of External Affairs said in the Ministry of External Affairs statement.\", \"imageUrl\": \"https://assets.inshorts.com/inshorts/images/v1/variants/jpg/m/2019/02_feb/27_wed/img_1551275947032_425.jpg?resize=400px:*\", \"readMoreUrl\": \"https://twitter.com/ANI/status/1100747033269415936?utm_campaign=fullarticle&utm_medium=referral&utm_source=inshorts \", \"timestamp\": 1551309720, \"trees\": [\"(S\\n (NNNP india/NNP)\\n on/IN\\n (NNNP wednesday/NNP)\\n (VVVV summon/VBD)\\n (NNNP pakistan/NNP acting/NNP high/NNP commissioner/NNP)\\n (VVVV object/VBD)\\n (NNNP pakistan/NNP)\\n (NIN (NNNN display/NN) of/IN (NNNN personnel/NNS))\\n of/IN\\n (NNNP indian/NNP air/NNP force/NNP))\", \"(S\\n (NNNP india/NNP)\\n (VVVV call/VBD)\\n (NNNP pakistan/NNP)\\n (NIN (NNNN act/NN violation/NN) of/IN (NNNN norm/NNS))\\n of/IN\\n (NNNP international/NNP humanitarian/NNP law/NNP geneva/NNP)\\n (NNNN convention/NN))\", \"(S\\n (NNNP india/NNP)\\n (VVVV expect/VBZ)\\n (NNNN immediate/NN return/NN)\\n (NNNP ministry/NNP)\\n of/IN\\n (NNNP external/NNP)\\n affair/NNPS\\n (VVVV say/VBD)\\n in/IN\\n (NNNP ministry/NNP)\\n of/IN\\n (NNNP external/NNP affair/NNP)\\n (NNNN statement/NN))\"], \"tokens\": [[\"india\", \"NNP\"], [\"on\", \"IN\"], [\"wednesday\", \"NNP\"], [\"summon\", \"VBD\"], [\"pakistan\", \"NNP\"], [\"acting\", \"NNP\"], [\"high\", \"NNP\"], [\"commissioner\", \"NNP\"], [\"and\", \"CC\"], [\"strongly\", \"RB\"], [\"object\", \"VBD\"], [\"to\", \"TO\"], [\"pakistan\", \"NNP\"], [\"vulgar\", \"FW\"], [\"display\", \"NN\"], [\"of\", \"IN\"], [\"injured\", \"JJ\"], [\"personnel\", \"NNS\"], [\"of\", \"IN\"], [\"indian\", \"NNP\"], [\"air\", \"NNP\"], [\"force\", \"NNP\"], [\"india\", \"NNP\"], [\"call\", \"VBD\"], [\"pakistan\", \"NNP\"], [\"act\", \"NN\"], [\"violation\", \"NN\"], [\"of\", \"IN\"], [\"norm\", \"NNS\"], [\"of\", \"IN\"], [\"international\", \"NNP\"], [\"humanitarian\", \"NNP\"], [\"law\", \"NNP\"], [\"and\", \"CC\"], [\"geneva\", \"NNP\"], [\"convention\", \"NNP\"], [\"india\", \"NNP\"], [\"expect\", \"VBZ\"], [\"his\", \"PRP$\"], [\"immediate\", \"NN\"], [\"and\", \"CC\"], [\"safe\", \"JJ\"], [\"return\", \"NN\"], [\"ministry\", \"NNP\"], [\"of\", \"IN\"], [\"external\", \"NNP\"], [\"affair\", \"NNPS\"], [\"say\", \"VBD\"], [\"in\", \"IN\"], [\"ministry\", \"NNP\"], [\"of\", \"IN\"], [\"external\", \"NNP\"], [\"affair\", \"NNPS\"], [\"statement\", \"NN\"]]}, {\"id\": 639, \"title\": \"What does the Geneva Convention say about Prisoners of War?\", \"content\": \"India on Wednesday objected to Pakistan's \\\"vulgar display\\\" of the injured IAF personnel in Pakistan's \\\"vulgar display\\\" of the injured IAF personnel in their custody custody, terming India a violation of the Geneva Convention. the Geneva Convention states a Prisoner of War (POW) cannot be physically or mentally tortured and must be provided with food, clothing, and medical care. Further, a POW must be released once hostilities between both sides end.\", \"imageUrl\": \"https://assets.inshorts.com/inshorts/images/v1/variants/jpg/m/2019/02_feb/27_wed/img_1551281387856_468.jpg?resize=400px:*\", \"readMoreUrl\": \"https://www.thenewsminute.com/article/explainer-what-geneva-convention-says-about-treatment-prisoners-war-97466?utm_campaign=fullarticle&utm_medium=referral&utm_source=inshorts \", \"timestamp\": 1551316860, \"trees\": [\"(S\\n (NNNP india/NNP)\\n on/IN\\n (NNNP wednesday/NNP)\\n (VVVV object/VBD)\\n (NNNP pakistan/NNP)\\n (NIN (NNNN display/NN) of/IN (NNNP iaf/NNP))\\n (NIN (NNNN personnel/NNS) in/IN (NNNP pakistan/NNP))\\n (NIN (NNNN display/NN) of/IN (NNNP iaf/NNP))\\n (NIN (NNNN personnel/NNS) in/IN (NNNN custody/NN custody/NN))\\n (VVVV term/VBG)\\n (NNNP india/NNP)\\n (NIN (NNNN violation/NN) of/IN (NNNP geneva/NNP))\\n (NNNN convention/NN))\", \"(S\\n (NNNP geneva/NNP convention/NNP)\\n (VVVV state/VBZ)\\n (NNNP prisoner/NNP)\\n of/IN\\n (NNNP war/NNP pow/NNP)\\n (VVVV be/VB be/VB provide/VBN)\\n with/IN\\n (NNNN food/NN clothing/NN care/NN))\", \"(S\\n (NNNP pow/NNP)\\n (VVVV be/VB release/VBN)\\n (NIN (NNNN hostility/NNS) between/IN (NNNN side/NNS))\\n (VVVV end/VBP))\"], \"tokens\": [[\"india\", \"NNP\"], [\"on\", \"IN\"], [\"wednesday\", \"NNP\"], [\"object\", \"VBD\"], [\"to\", \"TO\"], [\"pakistan\", \"NNP\"], [\"vulgar\", \"FW\"], [\"display\", \"NN\"], [\"of\", \"IN\"], [\"injured\", \"JJ\"], [\"iaf\", \"NNP\"], [\"personnel\", \"NNS\"], [\"in\", \"IN\"], [\"pakistan\", \"NNP\"], [\"vulgar\", \"FW\"], [\"display\", \"NN\"], [\"of\", \"IN\"], [\"injured\", \"JJ\"], [\"iaf\", \"NNP\"], [\"personnel\", \"NNS\"], [\"in\", \"IN\"], [\"their\", \"PRP$\"], [\"custody\", \"NN\"], [\"custody\", \"NN\"], [\"term\", \"VBG\"], [\"india\", \"NNP\"], [\"violation\", \"NN\"], [\"of\", \"IN\"], [\"geneva\", \"NNP\"], [\"convention\", \"NNP\"], [\"geneva\", \"NNP\"], [\"convention\", \"NNP\"], [\"state\", \"VBZ\"], [\"prisoner\", \"NNP\"], [\"of\", \"IN\"], [\"war\", \"NNP\"], [\"pow\", \"NNP\"], [\"can\", \"MD\"], [\"not\", \"RB\"], [\"be\", \"VB\"], [\"physically\", \"RB\"], [\"or\", \"CC\"], [\"mentally\", \"RB\"], [\"tortured\", \"JJ\"], [\"and\", \"CC\"], [\"must\", \"MD\"], [\"be\", \"VB\"], [\"provide\", \"VBN\"], [\"with\", \"IN\"], [\"food\", \"NN\"], [\"clothing\", \"NN\"], [\"and\", \"CC\"], [\"medical\", \"JJ\"], [\"care\", \"NN\"], [\"further\", \"NNP\"], [\"pow\", \"NNP\"], [\"must\", \"MD\"], [\"be\", \"VB\"], [\"release\", \"VBN\"], [\"once\", \"RB\"], [\"hostility\", \"NNS\"], [\"between\", \"IN\"], [\"side\", \"NNS\"], [\"end\", \"VBP\"]]}, {\"id\": 70, \"title\": \"Tracking 250 debris objects from ASAT test, ISS not at risk: US\", \"content\": \"The US is tracking 250-270 objects of space debris generated due to India's anti-satellite (ASAT) missile test in lower Earth orbit, but the International Space Station is not at risk, the Pentagon said on Friday. The US defence department added The US defence department will continue to track the debris and issue close approach notifications as required until The US defence department enters the Earth's atmosphere.\", \"imageUrl\": \"https://assets.inshorts.com/inshorts/images/v1/variants/jpg/m/2019/03_mar/30_sat/img_1553946788775_602.jpg?resize=400px:*\", \"readMoreUrl\": \"http://www.newindianexpress.com/world/2019/mar/30/us-tracking-250-270-objects-from-indian-asat-test-debris-iss-not-at-risk-pentagon-1957942.html?utm_campaign=fullarticle&utm_medium=referral&utm_source=inshorts \", \"timestamp\": 1553982900, \"trees\": [\"(S\\n (NNNP u/NNP)\\n (VVVV be/VBZ track/VBG)\\n (NIN (NNNN object/NNS) of/IN (NNNN space/NN debris/NN))\\n (VVVV generate/VBD)\\n (NNNP india/NNP asat/NNP)\\n (NIN (NNNN missile/NN test/NN) in/IN (NNNN earth/NN orbit/NN))\\n (NNNP international/NNP space/NNP station/NNP)\\n (VVVV be/VBZ)\\n at/IN\\n (NNNN risk/NN)\\n (NNNP pentagon/NNP)\\n (VVVV say/VBD)\\n on/IN\\n (NNNP friday/NNP))\", \"(S\\n (NNNP u/NNP)\\n (NNNN defence/NN department/NN)\\n (VVVV add/VBD)\\n (NNNP u/NNP)\\n (NNNN defence/NN department/NN)\\n (VVVV continue/VB track/VB)\\n (NNNN debris/NN issue/NN notification/NNS)\\n (NVIN a/IN (VVVV require/VBN) until/IN (NNNP u/NNP))\\n (NNNN defence/NN department/NN)\\n (VVVV enter/VBZ)\\n (NNNP earth/NNP)\\n (NNNN atmosphere/NN))\"], \"tokens\": [[\"u\", \"NNP\"], [\"be\", \"VBZ\"], [\"track\", \"VBG\"], [\"250-270\", \"JJ\"], [\"object\", \"NNS\"], [\"of\", \"IN\"], [\"space\", \"NN\"], [\"debris\", \"NN\"], [\"generate\", \"VBD\"], [\"due\", \"JJ\"], [\"to\", \"TO\"], [\"india\", \"NNP\"], [\"anti-satellite\", \"JJ\"], [\"asat\", \"NNP\"], [\"missile\", \"NN\"], [\"test\", \"NN\"], [\"in\", \"IN\"], [\"low\", \"JJR\"], [\"earth\", \"NN\"], [\"orbit\", \"NN\"], [\"but\", \"CC\"], [\"international\", \"NNP\"], [\"space\", \"NNP\"], [\"station\", \"NNP\"], [\"be\", \"VBZ\"], [\"not\", \"RB\"], [\"at\", \"IN\"], [\"risk\", \"NN\"], [\"pentagon\", \"NNP\"], [\"say\", \"VBD\"], [\"on\", \"IN\"], [\"friday\", \"NNP\"], [\"u\", \"NNP\"], [\"defence\", \"NN\"], [\"department\", \"NN\"], [\"add\", \"VBD\"], [\"u\", \"NNP\"], [\"defence\", \"NN\"], [\"department\", \"NN\"], [\"will\", \"MD\"], [\"continue\", \"VB\"], [\"to\", \"TO\"], [\"track\", \"VB\"], [\"debris\", \"NN\"], [\"and\", \"CC\"], [\"issue\", \"NN\"], [\"close\", \"RB\"], [\"approach\", \"JJ\"], [\"notification\", \"NNS\"], [\"a\", \"IN\"], [\"require\", \"VBN\"], [\"until\", \"IN\"], [\"u\", \"NNP\"], [\"defence\", \"NN\"], [\"department\", \"NN\"], [\"enter\", \"VBZ\"], [\"earth\", \"NNP\"], [\"atmosphere\", \"NN\"]]}, {\"id\": 487, \"title\": \"WikiLeaks source Chelsea Manning jailed for refusing to testify\", \"content\": \"Former US intelligence analyst Chelsea Manning has been jailed for refusing to testify before an investigation into WikiLeaks. Manning served seven years of a 35-year sentence for leaking secret military documents to WikiLeaks, with her sentence being commuted by former US President Barack Obama. Former US intelligence analyst Chelsea Manning has refused to testify as her objects to the secrecy of the grand jury process.\", \"imageUrl\": \"https://assets.inshorts.com/inshorts/images/v1/variants/jpg/m/2019/03_mar/9_sat/img_1552137329439_256.jpg?resize=400px:*\", \"readMoreUrl\": \"https://www.theguardian.com/us-news/2019/mar/08/chelsea-manning-judge-jails-wikileaks-case?utm_campaign=fullarticle&utm_medium=referral&utm_source=inshorts \", \"timestamp\": 1552175880, \"trees\": [\"(S\\n (NNNP former/NNP u/NNP)\\n (NNNN intelligence/NN analyst/NN)\\n (NNNP chelsea/NNP manning/NNP)\\n (VVVV have/VBZ be/VBN jail/VBN)\\n (NIN\\n (NVIN\\n for/IN\\n (VVVV refuse/VBG testify/VB)\\n before/IN\\n (NNNN investigation/NN))\\n into/IN\\n (NNNP wikileaks/NNP)))\", \"(S\\n (VVVV man/VBG serve/VBD)\\n (NIN (NNNN year/NNS) of/IN (NNNN sentence/NN))\\n for/IN\\n (VVVV leak/VBG)\\n (NNNN document/NNS)\\n (NNNP wikileaks/NNP)\\n with/IN\\n (NVIN\\n (NNNN sentence/NN)\\n (VVVV be/VBG commute/VBN)\\n by/IN\\n (NNNP u/NNP president/NNP barack/NNP obama/NNP)))\", \"(S\\n (NNNP former/NNP u/NNP)\\n (NNNN intelligence/NN analyst/NN)\\n (NNNP chelsea/NNP manning/NNP)\\n (VVVV have/VBZ refuse/VBN testify/VB)\\n a/IN\\n (NIN (NNNN object/NNS secrecy/NN) of/IN (NNNN jury/NN process/NN)))\"], \"tokens\": [[\"former\", \"NNP\"], [\"u\", \"NNP\"], [\"intelligence\", \"NN\"], [\"analyst\", \"NN\"], [\"chelsea\", \"NNP\"], [\"manning\", \"NNP\"], [\"have\", \"VBZ\"], [\"be\", \"VBN\"], [\"jail\", \"VBN\"], [\"for\", \"IN\"], [\"refuse\", \"VBG\"], [\"to\", \"TO\"], [\"testify\", \"VB\"], [\"before\", \"IN\"], [\"investigation\", \"NN\"], [\"into\", \"IN\"], [\"wikileaks\", \"NNP\"], [\"man\", \"VBG\"], [\"serve\", \"VBD\"], [\"seven\", \"CD\"], [\"year\", \"NNS\"], [\"of\", \"IN\"], [\"35-year\", \"JJ\"], [\"sentence\", \"NN\"], [\"for\", \"IN\"], [\"leak\", \"VBG\"], [\"secret\", \"JJ\"], [\"military\", \"JJ\"], [\"document\", \"NNS\"], [\"to\", \"TO\"], [\"wikileaks\", \"NNP\"], [\"with\", \"IN\"], [\"her\", \"PRP$\"], [\"sentence\", \"NN\"], [\"be\", \"VBG\"], [\"commute\", \"VBN\"], [\"by\", \"IN\"], [\"former\", \"JJ\"], [\"u\", \"NNP\"], [\"president\", \"NNP\"], [\"barack\", \"NNP\"], [\"obama\", \"NNP\"], [\"former\", \"NNP\"], [\"u\", \"NNP\"], [\"intelligence\", \"NN\"], [\"analyst\", \"NN\"], [\"chelsea\", \"NNP\"], [\"manning\", \"NNP\"], [\"have\", \"VBZ\"], [\"refuse\", \"VBN\"], [\"to\", \"TO\"], [\"testify\", \"VB\"], [\"a\", \"IN\"], [\"her\", \"PRP$\"], [\"object\", \"NNS\"], [\"to\", \"TO\"], [\"secrecy\", \"NN\"], [\"of\", \"IN\"], [\"grand\", \"JJ\"], [\"jury\", \"NN\"], [\"process\", \"NN\"]]}]}");
    }

    private void request(String query){
        searchButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        radar.removeAllViewsInLayout();
        articleListView.setVisibility(View.INVISIBLE);

        new Api(new Api.AsyncResponse() {
            @Override
            public void postExecute(String output) {
                handleResponse(output);
            }
        }
        ).execute(ip+"find/"+query);
    }

    private Article[] convert(String responseString){
        Article[] articles = null;

        try {
            JSONObject jsonObject = new JSONObject(responseString);
            if (!jsonObject.getString("result").equals("success"))
                throw new Exception("Server responded with failure");

            query = jsonObject.getString("lemmatizedQuery");
            JSONArray data = jsonObject.getJSONArray("data");
            articles = new Article[data.length()];

            for(int i=0;i<data.length();i++){
                jsonObject = data.getJSONObject(i);
                articles[i] = Article.deserialize(jsonObject);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return articles;
    }

    private ArrayList<String> convertTreeToPoints(String tree){
        Matcher nodeMatcher = Pattern.compile("[(][^SI ]+ [^)]+[)]").matcher(tree);
        Pattern p = Pattern.compile("\\/[^ ]+");
        HashSet<String> uniqueNodesForTree = new HashSet<>();
        ArrayList<String> nodesForTree = new ArrayList<>();

        String nodeString;
        String temp;
        while (nodeMatcher.find()){
            nodeString = nodeMatcher.group();
            tree = tree.replace(nodeString,"");
            nodeMatcher = nodeMatcher.reset(tree);

            Matcher cleanWord = p.matcher(nodeString);
            nodeString = cleanWord.replaceAll("");

            temp = nodeString.substring(nodeString.indexOf(' ')+1);
            if (uniqueNodesForTree.contains(temp))
                continue;
            uniqueNodesForTree.add(temp);
            nodesForTree.add(temp);
        }

        return nodesForTree;
    }

    public void handleResponse(String response) {
        searchButton.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        uniqueNodes = new LinkedHashSet<>();
        nodeMap = new HashMap<>();
        articles = convert(response);

        if (articles==null || articles.length==0 ) {
            Toast.makeText(getApplicationContext(),"No articles found!",Toast.LENGTH_LONG).show();
            return;
        }
        articleListView.setVisibility(View.VISIBLE);

        for(Article article:articles){
            article.setNodes(convertTreeToPoints(article.getTrees()[0])); //ASSUMING ALL ARTICLES HAS ATLEAST ONE TREE
            uniqueNodes.addAll(article.getNodes());
        }

        System.out.println(uniqueNodes);


//        if (uniqueNodes.contains(query)) {
//            Log.d("RA", "CONTAINS Q: "+query);
//            nodeMap.put(query,new Point(radar.getWidth()/2,radar.getHeight()/2));
//            uniqueNodes.remove(query);
//        }else{
//            Log.d("RA", "check PQ");
//            for(String partialQuery:query.split(" ")) {
//                Log.d("RA", "PQ: "+partialQuery);
//                if (uniqueNodes.contains(partialQuery)){
//                    Log.d("RA", "CONTAINS PQ: "+partialQuery);
//                    nodeMap.put(partialQuery,new Point(radar.getWidth()/2,radar.getHeight()/2));
//                    uniqueNodes.remove(partialQuery);
//                    break;
//                }
//            }
//        }
//
//        if (nodeMap.isEmpty()){
//            Log.e("ERROR","Cannot figure out center");
//        }

        Random r = new Random();
        for (String node:uniqueNodes){
            nodeMap.put(node, new Point(r.nextInt(radar.getWidth()-50)+25,r.nextInt(radar.getHeight()-50)+25));
        }

        Bitmap bitmap = Bitmap.createBitmap(radar.getWidth(),radar.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(bitmap);

        Paint grayColorBrush_FILL = new Paint(Paint.ANTI_ALIAS_FLAG);
        grayColorBrush_FILL.setColor(Color.LTGRAY);
        grayColorBrush_FILL.setStyle(Paint.Style.FILL);

        Path grid = new Path();
//        grid.moveTo(0,0);
//        grid.lineTo(radar.getWidth(),0);
//        grid.moveTo(radar.getWidth(),0);
//        grid.lineTo(radar.getWidth(),radar.getHeight());
//        grid.moveTo(radar.getWidth(),radar.getHeight());
//        grid.lineTo(0,radar.getHeight());
//        grid.moveTo(0,radar.getHeight());
//        grid.lineTo(0,0);
//        grid.moveTo(0,0);

        for (int gap=0;gap<=radar.getWidth();gap+=radar.getWidth()/5){
            grid.moveTo(gap,0);
            grid.lineTo(gap,radar.getHeight());
        }

        for (int gap=0;gap<=radar.getHeight();gap+=radar.getHeight()/5){
            grid.moveTo(0, gap);
            grid.lineTo(radar.getWidth(),gap);
        }

        grayColorBrush_FILL.setStyle(Paint.Style.STROKE);
        grayColorBrush_FILL.setColor(Color.LTGRAY);
        grayColorBrush_FILL.setStrokeWidth(3);
        canvas.drawPath(grid,grayColorBrush_FILL);

        grayColorBrush_FILL.setColor(ContextCompat.getColor(getApplicationContext(),R.color.colorAccent));
        grayColorBrush_FILL.setStyle(Paint.Style.FILL);

        for (String node:nodeMap.keySet()){

            canvas.drawCircle(nodeMap.get(node).x,nodeMap.get(node).y, 10,grayColorBrush_FILL);
        }

        radar.setBackground(new BitmapDrawable(getResources(),bitmap));

        articleAdapter = new ArticlesAdapter(getApplicationContext(),Arrays.asList(articles));
        articleListView.setAdapter(articleAdapter);
        articleListView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                onPageChange(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        articleListView.post(new Runnable() {
            @Override
            public void run() {
                onPageChange(0);
            }
        });
    }

    public void onPageChange(final int i){
        radar.removeAllViewsInLayout();
        radar.addView(new DrawView(getApplicationContext(),new DrawView.Draw() {
            @Override
            public Canvas drawThis(Canvas canvas) {

                Paint primaryColorBrush_FILL = new Paint(Paint.ANTI_ALIAS_FLAG);
                primaryColorBrush_FILL.setColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark));
                primaryColorBrush_FILL.setStyle(Paint.Style.FILL);
                primaryColorBrush_FILL.setShadowLayer(5, 0, 0, Color.BLACK);

                Paint primaryColorBrush_STROKE = new Paint(Paint.ANTI_ALIAS_FLAG);
                primaryColorBrush_STROKE.setColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark));
                primaryColorBrush_STROKE.setStyle(Paint.Style.STROKE);
                primaryColorBrush_STROKE.setStrokeWidth(10);
                primaryColorBrush_STROKE.setShadowLayer(5, 0, 0, Color.BLACK);

                Path path = new Path();

                ArrayList<String> nodes = new ArrayList<>(articles[i].getNodes());

                boolean first = true,pathCompleted = false;
                for(String node:nodes){
                    if (first){
                        path.moveTo(nodeMap.get(node).x, nodeMap.get(node).y);
                        first=!first;
                    }else {
                        path.lineTo(nodeMap.get(node).x, nodeMap.get(node).y);
                        path.moveTo(nodeMap.get(node).x, nodeMap.get(node).y);
                    }
                }
                canvas.drawPath(path,primaryColorBrush_STROKE);

                for(String node:nodes) {
                    canvas.drawCircle(nodeMap.get(node).x, nodeMap.get(node).y, 15, primaryColorBrush_FILL);
                    drawDigit(canvas,nodeMap.get(node).x, nodeMap.get(node).y,node);
                }

                for(String node:nodes) {
                    drawDigit(canvas,nodeMap.get(node).x, nodeMap.get(node).y,node);
                }
                return canvas;
            }
        }));
    }

    private void drawDigit(Canvas canvas,  float cX, float cY, String text) {
        Paint tempTextPaint = new Paint();
        tempTextPaint.setAntiAlias(true);
        tempTextPaint.setTextSize(30);
        tempTextPaint.setShadowLayer(5, 0, 0, Color.BLACK);

        text = ellipsize(text,20);
        float textWidth = tempTextPaint.measureText(text);
        //if cX and cY are the origin coordinates of the your rectangle
        //cX-(textWidth/2) = The x-coordinate of the origin of the text being drawn
        //cY+(textSize/2) =  The y-coordinate of the origin of the text being drawn

        RectF rect = new RectF( (int)(cX-(textWidth/2)-10),(int) cY+20,(int)(cX+(textWidth/2)+10), (int)cY+56);

        tempTextPaint.setStyle(Paint.Style.FILL);
        tempTextPaint.setColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark));
        canvas.drawRoundRect(rect,5,5,tempTextPaint);

        tempTextPaint.setColor(Color.WHITE);
        canvas.drawText(text, cX-(textWidth/2), cY+46, tempTextPaint);
    }

    private String ellipsize(String text, int max) {

        if (text.length() <= max)
            return text;

        // Start by chopping off at the word before max
        // This is an over-approximation due to thin-characters...
        int end = text.lastIndexOf(' ', max - 3);

        // Just one long word. Chop it off.
        if (end == -1)
            return text.substring(0, max-3) + "...";

        // Step forward as long as textWidth allows.
        int newEnd = end;
        do {
            end = newEnd;
            newEnd = text.indexOf(' ', end + 1);

            // No more spaces.
            if (newEnd == -1)
                newEnd = text.length();

        } while ((text.substring(0, newEnd) + "...").length() < max);

        return text.substring(0, end) + "...";
    }
}
