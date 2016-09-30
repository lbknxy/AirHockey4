package com.arcsoft.firstopenglproject;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.arcsoft.firstopenglproject.objects.Mallet;
import com.arcsoft.firstopenglproject.objects.Table;
import com.arcsoft.firstopenglproject.programs.ColorShaderProgram;
import com.arcsoft.firstopenglproject.programs.TextureShaderProgram;
import com.arcsoft.firstopenglproject.util.MatrixHelper;
import com.arcsoft.firstopenglproject.util.Shaderhelper;
import com.arcsoft.firstopenglproject.util.TextResourceReader;
import com.arcsoft.firstopenglproject.util.TextureHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.Matrix;
import android.opengl.Matrix.*;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_LINE_LOOP;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2016/9/26.
 */
public class AirHockeyRenderer implements GLSurfaceView.Renderer {

    private Context context;

    private Table table;
    private Mallet mallet;

    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix=new float[16];

    private TextureShaderProgram texturerProgram;
    private ColorShaderProgram colorProgram;

    private int texture;

    public AirHockeyRenderer(Context context) {

        this.context = context;

    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        table = new Table();
        mallet = new Mallet();

        texturerProgram = new TextureShaderProgram(context);
        colorProgram = new ColorShaderProgram(context);

        texture = TextureHelper.loadTexture(context,R.mipmap.air_hockey_surface);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);

        /*final float aspectRatio = width > height ?
                (float) width / (float) height :
                (float) height / (float) width;

        if(width>height){
            Matrix.orthoM(projectionMatrix,0,-aspectRatio,aspectRatio,-1f,1f,-1f,1f);
        }else{
            Matrix.orthoM(projectionMatrix,0,-1f,1f,-aspectRatio,aspectRatio,-1f,1f);
        }*/

        MatrixHelper.perspectiveM(projectionMatrix,45f,(float)width/(float)height,1,10);

        Matrix.setIdentityM(modelMatrix,0);
        Matrix.translateM(modelMatrix,0,0f,0f,-2f);

        final float[] temp=new float[16];

        Matrix.multiplyMM(temp,0,projectionMatrix,0,modelMatrix,0);

        Matrix.translateM(temp,0,0,0,-0.4f);
        Matrix.rotateM(temp,0,-40f,1f,0,0);

        System.arraycopy(temp,0,projectionMatrix,0,projectionMatrix.length);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);

        texturerProgram.useProgram();
        texturerProgram.setUniforms(projectionMatrix,texture);
        table.bindData(texturerProgram);
        table.draw();

        colorProgram.useProgram();
        colorProgram.setUniforms(projectionMatrix);
        mallet.bindData(colorProgram);
        mallet.draw();
    }
}
