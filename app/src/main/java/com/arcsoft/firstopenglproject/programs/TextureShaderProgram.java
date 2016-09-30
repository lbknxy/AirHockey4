package com.arcsoft.firstopenglproject.programs;

import android.content.Context;
import android.opengl.GLES20;

import com.arcsoft.firstopenglproject.R;

/**
 * Created by Administrator on 2016/9/30.
 */
public class TextureShaderProgram extends ShaderProgram{

    private int uMatrixLocation;
    private int uTextureUnitLocation;

    private int aPositionLocation;
    private int aTextureCoordinatesLocation;

    public TextureShaderProgram(Context context) {
        super(context, R.raw.texture_vertex_shader,R.raw.texture_fragment_shader);

        uMatrixLocation = GLES20.glGetUniformLocation(program,U_MATRIX);
        uTextureUnitLocation = GLES20.glGetUniformLocation(program,U_TEXTURE_UNIT);

        aPositionLocation = GLES20.glGetAttribLocation(program,A_POSITION);
        aTextureCoordinatesLocation = GLES20.glGetAttribLocation(program,A_TEXTURE_COORDINATES);

    }

    public void setUniforms(float[] matrix,int textureId){

        GLES20.glUniformMatrix4fv(uMatrixLocation,1,false,matrix,0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureId);

        GLES20.glUniform1f(uTextureUnitLocation,0);
    }

    public int getPositionAttributeLocation(){
        return aPositionLocation;
    }

    public int getaTextureCoordinatesLocation(){
        return aTextureCoordinatesLocation;
    }
}
