# FlowTagDemo
> 自定义控件，热门标签

![image](https://github.com/bysr/FlowTagDemo/blob/master/app/image/image.gif)

---
# 使用方法
1. 将存储库添加到gradle文件中
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

2. 添加依赖关系

```
	dependencies {
	        compile 'com.github.bysr:FlowTagDemo:v1.0.1'
	}

```

3. 布局文件中引用
```
    <hipad.flowtab.SimpleFlowLayout
        android:id="@+id/texts"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:backgroundDefault="@drawable/shape_corner"
        app:backgroundSelect="@drawable/shape_corner_choose"
        app:markTextSize="15dp"
        app:toMargin="10dp"
        app:toPadding="10dp"></hipad.flowtab.SimpleFlowLayout>
```
> 属性说明

标签名称 | 说明
---|---
backgroundDefault | 标签默认背景
backgroundSelect | 标签选中背景
markTextSize | 标签文字大小
toMargin | 统一设置的外间距
toLeftMargin | 左侧外间距
toTopMargin | 顶部外间距
toRightMargin | 右侧外间距
toBottomMargin | 底部外间距
toPadding | 统一设置的内间距
toLeftPadding | 左边内间距
toTopPadding | 顶部内间距
toRightPadding | 右侧内间距
toBottomPadding | 底部内间距

4. 使用方法
- 设置标签内容
```
      final SimpleFlowLayout texts = (SimpleFlowLayout) findViewById(R.id.texts);

        final List<String> list = new ArrayList<>();
        list.add("fdafafdaffdafafdafafafafd");
        list.add("25+656+5");
        list.add("12");
        list.add("5");
        list.add("fdafda");
        list.add("哈哈");
        list.add("dafafhkdahfudkah");
        
        //设置到控件中
        texts.setmTexts(list);
```
- 获取选中的标签集合
```
    List<String> list1 = texts.getChooseTexts();
```
- 选中或者取消选中的点击事件

```
texts.setOnClickChildListener(new SimpleFlowLayout.OnClickChildListener() {
            @Override
            public void OnClickChild(int position, boolean ischeck) {

         //position 操作的当前对象
         //ischeck 选中/取消选中


            }
        });
```

- 完整例子


```
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SimpleFlowLayout texts = (SimpleFlowLayout) findViewById(R.id.texts);

        final List<String> list = new ArrayList<>();
        list.add("fdafafdaffdafafdafafafafd");
        list.add("25+656+5");
        list.add("12");
        list.add("5");
        list.add("fdafda");
        list.add("哈哈");
        list.add("dafafhkdahfudkah");
        texts.setmTexts(list);

        texts.setOnClickChildListener(new SimpleFlowLayout.OnClickChildListener() {
            @Override
            public void OnClickChild(int position, boolean ischeck) {

                List<String> list1 = texts.getChooseTexts();

                StringBuffer buffer = new StringBuffer();


                for (String s : list1) {
                    buffer.append(s).append(",");
                }

                Toast.makeText(MainActivity.this, buffer.toString(), Toast.LENGTH_SHORT).show();


            }
        });

    }
```
