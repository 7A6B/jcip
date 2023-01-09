package com.jcip.chapter6;

import java.util.ArrayList;
import java.util.List;

public abstract class SingleThreadRender {
    void renderPage(CharSequence source){
        renderText(source);
        List<ImageData> imageData = new ArrayList<>();
        for (ImageInfo imageInfo : scanForImageInfo(source)) {
            imageData.add(imageInfo.downloadImage());
        }
        for (ImageData data : imageData) {
            renderImage(data);
        }

    }
    interface ImageData{}
    interface ImageInfo{
        ImageData downloadImage();
    }
    abstract void renderText(CharSequence s);
    abstract List<ImageInfo> scanForImageInfo(CharSequence s);
    abstract void renderImage(ImageData i);
}
