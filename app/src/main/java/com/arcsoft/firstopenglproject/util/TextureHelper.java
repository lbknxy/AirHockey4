package com.arcsoft.firstopenglproject.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static android.opengl.GLES20.GL_LINEAR;
import static android.opengl.GLES20.GL_LINEAR_MIPMAP_LINEAR;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glCompressedTexImage2D;
import static android.opengl.GLES20.glDeleteTextures;
import static android.opengl.GLES20.glGenTextures;
import static android.opengl.GLES20.glGenerateMipmap;
import static android.opengl.GLES20.glTexImage2D;
import static android.opengl.GLES20.glTexParameteri;
import static android.opengl.GLUtils.texImage2D;
/**
 * Created by Administrator on 2016/9/29.
 */
public class TextureHelper {

    private static String TAG = "TextureHelper";

    public static int loadTexture(Context context,int resourceId){

        final int[] textureObjectId = new int[1];

        glGenTextures(1,textureObjectId,0);

        if(textureObjectId[0]==0){
            LoggerConfig.e(TAG,"could not create a new texture object");

            return 0;
        }

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled=false;
        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),resourceId,options);

        if(bitmap==null){
            LoggerConfig.e(TAG,"Resource Id : " + resourceId +" could not be decoded.");
            glDeleteTextures(1,textureObjectId,0);
            return 0;
        }

        glBindTexture(GL_TEXTURE_2D,textureObjectId[0]); //bind

        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_LINEAR);


        texImage2D(GL_TEXTURE_2D,0,bitmap,0); //load bitmap to textID

        bitmap.recycle();

        glGenerateMipmap(GL_TEXTURE_2D); //generate mipmap.

        glBindTexture(GL_TEXTURE_2D,0); // unbind.

        return textureObjectId[0];
    }
}
