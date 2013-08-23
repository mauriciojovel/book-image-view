package com.madXdesign.book.image.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;

import com.madXdesign.book.image.view.drawable.TextDrawable;

import fi.harism.curl.CurlPage;
import fi.harism.curl.CurlView;

public class MainActivity extends Activity {
    
    private CurlView pageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //
        pageView = (CurlView) findViewById(R.id.page);
        pageView.setPageProvider(new PageProvider());
        pageView.setSizeChangedObserver(new SizeChangedObserver());
        pageView.setCurrentIndex(0);
        pageView.setBackgroundColor(0xFF202830);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /*public BitmapDrawable textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        int width = (int) (paint.measureText(text) + 0.5f); // round
        float baseline = (int) (-paint.ascent() + 0.5f); // ascent() is negative
        int height = (int) (baseline + paint.descent() + 0.5f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return new BitmapDrawable(getResources(), image);
    }*/
    
    /**
     * Bitmap provider.
     */
    private class PageProvider implements CurlView.PageProvider {

        // Bitmap resources.
        private String[] mBitmapIds = {
                    "Android es un sistema operativo basado en Linux,"
                 +" diseñado principalmente para dispositivos móviles"
                 +" con pantalla táctil como teléfonos inteligentes o tabletas inicialmente desarrollados por Android, Inc., que Google respaldó económicamente y más tarde compró en 2005,11 Android fue presentado en 2007 junto la fundación del Open Handset Alliance: un consorcio de compañías de hardware, software y telecomunicaciones para avanzar en los estándares abiertos de los dispositivos móviles.12 El primer móvil con el sistema operativo Android fue el HTC Dream y se vendió en octubre de 2008.13"
                    ,"La ciruela de huesito o jocote (Spondias purpurea) es un árbol frutal que crece en las zonas tropicales de América, desde México hasta Brasil. Su nombre deriva del idioma Náhuatl xocotl, que significa fruta."
                    ,"El perro o perro doméstico —cuyo nombre científico es Canis lupus familiaris1 2 3 4 —, es un mamífero carnívoro de la familia de los cánidos, que constituye una subespecie del lobo (Canis lupus). Gracias al proceso de domesticación, un estudio publicado por la revista de divulgación científica «Nature» revela que su organismo se ha adaptado5 a cierta clase de alimentos, en este caso el almidón,6 lo que no implica que por tal adaptación el perro se ha vuelto omnívoro —sigue siendo clasificado por los organismos científicos dentro del orden de los carnívoros— sino que más bien es alimentado como si lo fuera.7 Su tamaño o talla, su forma y pelaje es muy diverso según la raza de perro. Posee un oído y olfato muy desarrollados, siendo este último su principal órgano sensorial. En las razas pequeñas puede alcanzar una longevidad de cerca de 20 años, con atención esmerada por parte del propietario, de otra forma su vida en promedio es alrededor de los 15 años."
                    ,"El gato o gato doméstico (Felis silvestris catus) es un pequeño mamífero carnívoro de la familia Felidae. El gato está en convivencia cercana al ser humano desde hace unos 9500 años,1 periodo superior al estimado anteriormente, que oscilaba entre 3500 y 8000 años. En las lenguas romances los nombres actuales más generalizados derivan del latín vulgar catus, palabra que aludía especialmente a los gatos salvajes en contraposición a los gatos domésticos que, en latín, eran llamados felis."
                    ,"El dragón (del latín draco, y este del griego d?a???, drakon, ‘víbora’ o ‘serpiente’) es un animal mitológico que aparece en diversas formas en varias culturas de todo el mundo, con diferentes simbolismos asociados. Las interpretaciones más familiares de dragones son los dragones europeos, derivados de la tradición popular y de la mitología de Grecia, Escandinavia y Oriente Próximo, y también los dragones orientales. La palabra dragón deriva del griego d????? (drákon), \"dragón, serpiente de gran tamaño, o serpiente de agua\", que probablemente viene del verbo d?a?e?? \"ver claramente\"."
                };

        @Override
        public int getPageCount() {
            return 5;
        }
        
        private CharSequence splitText(String string, int width,int height, float textSize
                ,TextDrawable p) 
        {
            StringBuffer b = new StringBuffer();
            String temp = "";
            String[] words = string.split(" ");
            float widthPaint = 0;
            for (String word : words) {
                temp += word+" ";
                widthPaint = p.widthText(temp);
                if(widthPaint < width) {
                    b.append(word);
                    b.append(" ");
                } else {
                    b.append("\n");
                    b.append(word);
                    b.append(" ");
                    temp = word + " ";
                }
            }
            Log.i("ttt", "Total paginas: " + Math.ceil(p.heightText(b.toString())/(float)height));
            return b.toString();
        }
        
        private Bitmap loadBitmap(int width, int height, int index) {
            Bitmap b = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);
            b.eraseColor(0xFFFFFFFF);
            Canvas c = new Canvas(b);
            TextDrawable d = new TextDrawable(MainActivity.this);
            d.setText(splitText(mBitmapIds[index], width, height, d.getTextSize()
                    , d));
            Log.i("ttt", "bitmap width: " + width + " bitmap height: " + height);
            d.setTextAlign(Layout.Alignment.ALIGN_NORMAL);
            d.setTextColor(Color.BLACK);
            //Drawable d = t;

            int margin = 7;
            int border = 3;
            Rect r = new Rect(margin, margin, width - margin, height - margin);

            int imageWidth = r.width() - (border * 2);
            int imageHeight = imageWidth * d.getIntrinsicHeight()
                    / d.getIntrinsicWidth();
            if (imageHeight > r.height() - (border * 2)) {
                imageHeight = r.height() - (border * 2);
                imageWidth = imageHeight * d.getIntrinsicWidth()
                        / d.getIntrinsicHeight();
            }

            r.left += ((r.width() - imageWidth) / 2) - border;
            r.right = r.left + imageWidth + border + border;
            r.top += ((r.height() - imageHeight) / 2) - border;
            r.bottom = r.top + imageHeight + border + border;

            /*Paint p = new Paint();
            p.setColor(0xFFC0C0C0);
            c.drawRect(r, p);
            r.left += border;
            r.right -= border;
            r.top += border;
            r.bottom -= border;

            d.setBounds(r);*/
            d.draw(c);

            return b;
        }

        

        @Override
        public void updatePage(CurlPage page, int width, int height, int index) {
            Bitmap front = loadBitmap(width, height, index);
            //page.setTexture(front, CurlPage.SIDE_FRONT);
            page.setTexture(front, CurlPage.SIDE_BOTH);
            /*if(index > 0) {
                Bitmap back = loadBitmap(width, height, index-1);
                page.setTexture(back, CurlPage.SIDE_BACK);
            } else {
                //page.setColor(Color.WHITE, CurlPage.SIDE_BACK);
                //page.setTexture(front, CurlPage.SIDE_BACK);
            }*/
//            switch (index) {
//            // First case is image on front side, solid colored back.
//            case 0: {
//                Bitmap front = loadBitmap(width, height, 0);
//                page.setTexture(front, CurlPage.SIDE_FRONT);
//                page.setColor(Color.rgb(180, 180, 180), CurlPage.SIDE_BACK);
//                break;
//            }
//            // Second case is image on back side, solid colored front.
//            case 1: {
//                Bitmap back = loadBitmap(width, height, 2);
//                page.setTexture(back, CurlPage.SIDE_BACK);
//                page.setColor(Color.rgb(127, 140, 180), CurlPage.SIDE_FRONT);
//                break;
//            }
//            // Third case is images on both sides.
//            case 2: {
//                Bitmap front = loadBitmap(width, height, 1);
//                Bitmap back = loadBitmap(width, height, 3);
//                page.setTexture(front, CurlPage.SIDE_FRONT);
//                page.setTexture(back, CurlPage.SIDE_BACK);
//                break;
//            }
//            // Fourth case is images on both sides - plus they are blend against
//            // separate colors.
//            case 3: {
//                Bitmap front = loadBitmap(width, height, 2);
//                Bitmap back = loadBitmap(width, height, 1);
//                page.setTexture(front, CurlPage.SIDE_FRONT);
//                page.setTexture(back, CurlPage.SIDE_BACK);
//                page.setColor(Color.argb(127, 170, 130, 255),
//                        CurlPage.SIDE_FRONT);
//                page.setColor(Color.rgb(255, 190, 150), CurlPage.SIDE_BACK);
//                break;
//            }
//            // Fifth case is same image is assigned to front and back. In this
//            // scenario only one texture is used and shared for both sides.
//            case 4:
//                Bitmap front = loadBitmap(width, height, 0);
//                page.setTexture(front, CurlPage.SIDE_BOTH);
//                page.setColor(Color.argb(127, 255, 255, 255),
//                        CurlPage.SIDE_BACK);
//                break;
//            }
        }

    }

    /**
     * CurlView size changed observer.
     */
    private class SizeChangedObserver implements CurlView.SizeChangedObserver {
        @Override
        public void onSizeChanged(int w, int h) {
            if (w > h) {
                pageView.setViewMode(CurlView.SHOW_TWO_PAGES);
                pageView.setMargins(.01f, .05f, .01f, .05f);
            } else {
                pageView.setViewMode(CurlView.SHOW_ONE_PAGE);
                pageView.setMargins(.01f, .01f, .01f, .01f);
            }
        }
    }

}
