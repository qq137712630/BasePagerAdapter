# BasePagerAdapter
一个通用的PagerAdapter，在basepageradapterlibrary中，主要是仿[base-adapter-helper的RecyclerView版](http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0809/3277.html)，也是差不多的实现方式，例子是用[RecyclerTabLayout](https://github.com/nshmura/RecyclerTabLayout)

##使用
DemoBasicActivity

 - BasePagerAdapter

		BasePagerAdapter baseAdapter = new BasePagerAdapter<ColorItem>(items, strs, layouts) {
	
	        @Override
	        protected void convert(View view, ColorItem item, int position) {
	
	            TextView textView = (TextView) view.findViewById(R.id.title);
	            textView.setText("Page: " + item.hex);
	        }
	    };

 - QuickPagerAdapter

	    QuickPagerAdapter mQuickPagerAdapter = new QuickPagerAdapter<ColorItem>(items, strs, layouts) {
	
	        @Override
	        protected void convert(BasePagerAdapterHelper helper, ColorItem item, int position) {
	
	            TextView textView = helper.getTextView(R.id.title);
	            textView.setText("Page: " + item.hex);
	        }
	    };

##实现注意
实现PagerAdapter时，在```` instantiateItem ````中，记得用```` container.addView(view); ````。    
我忘了这个加上```` container.addView(view); ````，结果没法看到界面，还一卡一卡的。

BasePagerAdapter

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(layoutResIds.get(position % layoutResIds.size()), container, false);

        convert(view, getItem(position),position);
        container.addView(view);
        return view;
    }