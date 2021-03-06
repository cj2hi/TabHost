package com.killer.tabhost.pagewidget;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.killer.tabhost.R;

public class PageWidgetAdapter1 extends BaseAdapter {
	private final static String TAG = "PageWidgetAdapter1";

	private Context mContext;
	private TextView tv_title, tv_author, tv_content_up, tv_content_down, tv_pagenum;
	private ImageView iv_img;


	private int count;
	private LayoutInflater inflater;
	
	public PageWidgetAdapter1(Context context) {
		Log.d(TAG, "PageWidgetAdapter1");
		mContext = context;
		inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		count = 7;
	}
	@Override
	public int getCount() {
		return count;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d(TAG, "getView position="+position);
		ViewGroup layout ;
		if(convertView == null) {
			layout = (ViewGroup) inflater.inflate(R.layout.item_page1, null);

		} else {
			layout = (ViewGroup) convertView;
		}
		init(layout);
		switch (position) {
			case 0:
				setFirstPage(position);
				break;
			case 1:
				setSecondPage(position);
				break;
			case 2:
				setThirdPage(position);
				break;
			case 3:
				setFourthPage(position);
				break;
			case 4:
				setFifthPage(position);
				break;
			case 5:
				setSixthPage(position);
				break;
			case 6:
				setSeventhPage(position);
				break;

		}
		
		return layout;
	}

	private void init(ViewGroup layout) {
		tv_title = (TextView) layout.findViewById(R.id.tv_title);
		tv_author = (TextView) layout.findViewById(R.id.tv_author);
		tv_content_up = (TextView) layout.findViewById(R.id.tv_content_up);
		iv_img = (ImageView) layout.findViewById(R.id.iv_img);
		tv_content_down = (TextView) layout.findViewById(R.id.tv_content_down);
		tv_pagenum = (TextView) layout.findViewById(R.id.tv_pagenum);
	}

	private void setFirstPage(int position) {
		tv_title.setText("桨声灯影里的秦淮河");
		tv_title.setVisibility(View.VISIBLE);
		tv_author.setText("朱自清");
		tv_author.setVisibility(View.VISIBLE);
		tv_content_up.setVisibility(View.GONE);
		iv_img.setImageResource(R.drawable.img01);
		iv_img.setVisibility(View.VISIBLE);
		tv_content_down.setText("　　一九二三年八月的一晚，我和平伯同游秦淮河；平伯是初泛，我是重来了。我们雇了一只“七板子”，在夕阳已去，皎月方来的时候，便下了船。于是桨声汩——汩，我们开始领略那晃荡着蔷薇色的历史的秦淮河的滋味了。\n　　秦淮河里的船，比北京万甡园，颐和园的船好，比西湖的船好，比扬州瘦西湖的船也好。这几处的船不是觉着笨，就是觉着简陋、局促；都不能引起乘客们的情韵，如秦淮河的船一样。秦淮河的船约略可分为两种：一是大船；一是小船，就是所谓“七板子”。大船舱口阔大，可容二三十人。里面陈设着字画和光");
		tv_content_down.setVisibility(View.VISIBLE);
		tv_pagenum.setText(String.valueOf(position+1));
	}

	private void setSecondPage(int position) {
		tv_title.setVisibility(View.GONE);
		tv_author.setVisibility(View.GONE);
		tv_content_up.setText("洁的红木家具，桌上一律嵌着冰凉的大理石面。窗格雕镂颇细，使人起柔腻之感。窗格里映着红色蓝色的玻璃；玻璃上有精致的花纹，也颇悦人目。“七板子”规模虽不及大船，但那淡蓝色的栏杆，空敞的舱，也足系人情思。而最出色处却在它的舱前。舱前是甲板上的一部。上面有弧形的顶，两边用疏疏的栏杆支着。里面通常放着两张藤的躺椅。躺下，可以谈天，可以望远，可以顾盼两岸的河房。大船上也有这个，便在小船上更觉清隽罢了。舱前的顶下，一律悬着灯彩；灯的多少，明暗，彩苏的精粗，艳晦，是不一的。但好歹总还你一个灯彩。这灯彩实在是最能钩人的东西。夜幕垂垂地下来时，大小船上都点起灯火。从两重玻璃里映出那辐射着的黄黄的散光，反晕出一片朦胧的烟霭；透过这烟霭，在黯黯的水波里，又逗起缕缕的明漪。在这薄霭和微漪里，听着那悠然的间歇的桨声，谁能不被引入他的美梦去呢？只愁梦太多了，这些大小船儿如何载得起呀？我们这时模模糊糊的谈着明末的秦淮河的艳迹，如《桃花扇》及《板桥杂记》里所载的。我们真神往了。我们仿佛亲见那时华灯映水，画舫凌波的光景了。于是");
		tv_content_up.setVisibility(View.VISIBLE);
		iv_img.setVisibility(View.GONE);
		tv_content_down.setVisibility(View.GONE);
		tv_pagenum.setText(String.valueOf(position+1));
	}

	private void setThirdPage( int position) {
		tv_title.setVisibility(View.GONE);
		tv_author.setVisibility(View.GONE);
		tv_content_up.setText("我们的船便成了历史的重载了。我们终于恍然秦淮河的船所以雅丽过于他处，而又有奇异的吸引力的，实在是许多历史的影象使然了。");
		tv_content_up.setVisibility(View.VISIBLE);
		iv_img.setImageResource(R.drawable.img02);
		iv_img.setVisibility(View.VISIBLE);
		tv_content_down.setText("　　秦淮河的水是碧阴阴的；看起来厚而不腻，或者是六朝金粉所凝么？我们初上船的时候，天色还未断黑，那漾漾的柔波是这样的恬静，委婉，使我们一面有水阔天空之想，一面又憧憬着纸醉金迷之境了。等到灯火明时，阴阴的变为沉沉了：黯淡的水光，像梦一般；那偶然闪烁着的光芒，就是梦的眼睛了。我们坐在舱前，因了那隆起的顶棚，仿佛总是昂着首向前走着似的；于是飘飘然如御风而行的我们，看着那些自在的湾泊着的船，船里走马灯般的人物，便像是下界一般，迢迢的远了，又像在雾里看花，尽朦朦胧胧的。这时我们已过");
		tv_content_down.setVisibility(View.VISIBLE);
		tv_pagenum.setText(String.valueOf(position+1));
	}

	private void setFourthPage( int position) {
		tv_title.setVisibility(View.GONE);
		tv_author.setVisibility(View.GONE);
		tv_content_up.setText("了利涉桥，望见东关头了。沿路听见断续的歌声：有从沿河的妓楼飘来的，有从河上船里度来的。我们明知那些歌声，只是些因袭的言词，从生涩的歌喉里机械的发出来的；但它们经了夏夜的微风的吹漾和水波的摇拂，袅娜着到我们耳边的时候，已经不单是她们的歌声，而混着微风和河水的密语了。于是我们不得不被牵惹着，震撼着，相与浮沉于这歌声里了。从东关头转湾，不久就到大中桥。大中桥共有三个桥拱，都很阔大，俨然是三座门儿；使我们觉得我们的船和船里的我们，在桥下过去时，真是太无颜色了。桥砖是深褐色，表明它的历史的长久；但都完好无缺，令人太息于古昔工程的坚美。桥上两旁都是木壁的房子，中间应该有街路？这些房子都破旧了，多年烟熏的迹，遮没了当年的美丽。我想象秦淮河的极盛时，在这样宏阔的桥上，特地盖了房子，必然是髹漆得富富丽丽的；晚间必然是灯火通明的。现在却只剩下一片黑沉沉！但是桥上造着房子，毕竟使我们多少可以想见往日的繁华；这也慰情聊胜无了。过了大中桥，便到了灯月交辉，笙歌彻夜的秦淮河；这才是秦淮河的真面目哩。");
		tv_content_up.setVisibility(View.VISIBLE);
		iv_img.setVisibility(View.GONE);
		tv_content_down.setVisibility(View.GONE);
		tv_pagenum.setText(String.valueOf(position+1));
	}

	private void setFifthPage( int position) {
		tv_title.setVisibility(View.GONE);
		tv_author.setVisibility(View.GONE);
		tv_content_up.setVisibility(View.GONE);
		iv_img.setImageResource(R.drawable.img03);
		iv_img.setVisibility(View.VISIBLE);
		tv_content_down.setText("　　大中桥外，顿然空阔，和桥内两岸排着密密的人家的大异了。一眼望去，疏疏的林，淡淡的月，衬着蓝蔚的天，颇像荒江野渡光景；那边呢，郁丛丛的，阴森森的，又似乎藏着无边的黑暗：令人几乎不信那是繁华的秦淮河了。但是河中眩晕着的灯光，纵横着的画舫，悠扬着的笛韵，夹着那吱吱的胡琴声，终于使我们认识绿如茵陈如酒的秦淮水了。此地天裸露着的多些，故觉夜来的独迟些；从清清的水影里，我们感到的只是薄薄的夜——这正是秦淮河的夜。大中桥外，本来还有一座复成桥，是船夫口中的我们的游踪尽处，或也是秦");
		tv_content_down.setVisibility(View.VISIBLE);
		tv_pagenum.setText(String.valueOf(position+1));
	}

	private void setSixthPage( int position) {
		tv_title.setVisibility(View.GONE);
		tv_author.setVisibility(View.GONE);
		tv_content_up.setText("淮河繁华的尽处了。我的脚曾踏过复成桥的脊，在十三四岁的时候。但是两次游秦淮河，却都不曾见着复成桥的面；明知总在前途的，却常觉得有些虚无缥缈似的。我想，不见倒也好。这时正是盛夏。我们下船后，借着新生的晚凉和河上的微风，暑气已渐渐消散；到了此地，豁然开朗，身子顿然轻了——习习的清风荏苒在面上，手上，衣上，这便又感到了一缕新凉了。南京的日光，大概没有杭州猛烈；西湖的夏夜老是热蓬蓬的，水像沸着一般，秦淮河的水却尽是这样冷冷地绿着。任你人影的憧憧，歌声的扰扰，总像隔着一层薄薄的绿纱面幂似的；它尽是这样静静的，冷冷的绿着。我们出了大中桥，走不上半里路，船夫便将船划到一旁，停了桨由它宕着。他以为那里正是繁华的极点，再过去就是荒凉了；所以让我们多多赏鉴一会儿。他自己却静静地蹲着。他是看惯这光景的了，大约只是一个无可无不可。这无可无不可，无论是升的沉的，总之，都比我们高了。");
		tv_content_up.setVisibility(View.VISIBLE);
		iv_img.setVisibility(View.GONE);
		tv_content_down.setText("　　那时河里热闹极了；船大半泊着，小半在水上穿梭似的来往。停泊着的都在近市的那一");
		tv_content_down.setVisibility(View.VISIBLE);
		tv_pagenum.setText(String.valueOf(position+1));
	}

	private void setSeventhPage( int position) {
		tv_title.setVisibility(View.GONE);
		tv_author.setVisibility(View.GONE);
		tv_content_up.setText("边，我们的船自然也夹在其中。因为这边略略的挤，便觉得那边十分的疏了。在每一只船从那边过去时，我们能画出它的轻轻的影和曲曲的波，在我们的心上；这显着是空，且显着是静了。那时处处都是歌声和凄厉的胡琴声，圆润的喉咙，确乎是很少的。但那生涩的，尖脆的调子能使人有少年的，粗率不拘的感觉，也正可快我们的意。况且多少隔开些儿听着，因为想象与渴慕的做美，总觉更有滋味；而竞发的喧嚣，抑扬的不齐，远近的杂沓，和乐器的嘈嘈切切，合成另一意味的谐音，也使我们无所适从，如随着大风而走。这实在因为我们的心枯涩久了，变为脆弱；故偶然润泽一下，便疯狂似的不能自主了。但秦淮河确也腻人。即如船里的人面，无论是和我们一堆儿泊着的，无论是从我们眼前过去的，总是模模糊糊的，甚至渺渺茫茫的；任你张圆了眼睛，揩净了眦垢，也是枉然。这真够人想呢。在我们停泊的地方，灯光原是纷然的；不过这些灯光都是黄而有晕的。黄已经不能明了，再加上了晕，便更不成了。灯愈多，晕就愈甚；在繁星般的黄的交错里，秦淮河仿佛笼上了一团光雾。");
		tv_content_up.setVisibility(View.VISIBLE);
		iv_img.setVisibility(View.GONE);
		tv_content_down.setVisibility(View.GONE);
		tv_pagenum.setText(String.valueOf(position+1));
	}

}
