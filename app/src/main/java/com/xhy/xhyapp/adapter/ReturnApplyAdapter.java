package com.xhy.xhyapp.adapter;

/**
 * Created by Administrator on 2016/7/28 0028.
 */
//public class ReturnApplyAdapter extends BaseAdapter {
//    Context context;
//    private List<Integer> images = new ArrayList<>();
//
//    public ReturnApplyAdapter(Context context, List<Integer> images) {
//        this.context = context;
//        this.images = images;
//    }
//
//    @Override
//    public int getCount() {
//        return images.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return images.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        ViewHolder holder = null;
//        if (view == null) {
//            holder = new ViewHolder();
//            view = View.inflate(context, R.layout.prove_item, null);
//            holder.image_prove = (ImageView) view.findViewById(R.id.image_prove);
//            view.setTag(holder);
//        } else {
//            holder = (ViewHolder) view.getTag();
//        }
//        holder.image_prove.setImageResource(images.get(i));
//        final SquareCenterImageView imageView = new SquareCenterImageView(context);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        ImageLoader.getInstance().displayImage(images.get(i), imageView);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, SpaceImageDetailActivity.class);
//                intent.putExtra("images", (ArrayList < > ()) images);
//                intent.putExtra("position", i);
//                int[] location = new int[2];
//                imageView.getLocationOnScreen(location);
//                intent.putExtra("locationX", location[0]);
//                intent.putExtra("locationY", location[1]);
//
//                intent.putExtra("width", imageView.getWidth());
//                intent.putExtra("height", imageView.getHeight());
//                startActivity(intent);
//                overridePendingTransition(0, 0);
//            }
//
//            private void overridePendingTransition(int i, int i1) {
//            }
//        });
//        return view;
//    }
//
//    class ViewHolder {
//        ImageView image_prove;
//    }
//}
