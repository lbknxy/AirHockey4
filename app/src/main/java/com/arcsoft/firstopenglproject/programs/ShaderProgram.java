package com.arcsoft.firstopenglproject.programs;

import android.content.Context;
import android.opengl.GLES20;

import com.arcsoft.firstopenglproject.util.Shaderhelper;
import com.arcsoft.firstopenglproject.util.TextResourceReader;

/**
 * Created by Administrator on 2016/9/30.
 */
public class ShaderProgram {

    protected static final String U_MATRIX ="u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";

    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_COLOR";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    protected int program;

    protected ShaderProgram(Context context ,int vertexShaderResourceId,int fragmentShaderResourceId){

        program = Shaderhelper.buildProgram(TextResourceReader.readTextFileFromResource(context,vertexShaderResourceId),
                TextResourceReader.readTextFileFromResource(context,fragmentShaderResourceId)
                );

    }

    public void useProgram(){
        GLES20.glUseProgram(program);
    }
}
