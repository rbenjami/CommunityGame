package com.engine;

import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

/**
 * Created on 13/04/14.
 */
public class Shaders
{
	private ArrayList<Integer> shaderList;
	private int programShader = 0;
	private int offsetUniform;

	public Shaders()
	{
		shaderList = new ArrayList<>();
	}

	public void createProgram() throws Exception
	{
		if ( shaderList.isEmpty() )
			throw new Exception( "Empty shader list !" );
		programShader = initProgram( shaderList );

		for ( Integer shader : shaderList )
		{
			glDeleteShader( shader );
		}
		offsetUniform = glGetUniformLocation(programShader, "offset");

		int perspectiveMatrixUnif = glGetUniformLocation(programShader, "perspectiveMatrix");

		float frustumScale = 1.0f; float zNear = 0.5f; float zFar = 3.0f;

		float theMatrix[] = new float[16];
		theMatrix[0] 	= frustumScale;
		theMatrix[5] 	= frustumScale;
		theMatrix[10] 	= (zFar + zNear) / (zNear - zFar);
		theMatrix[14] 	= (2 * zFar * zNear) / (zNear - zFar);
		theMatrix[11] 	= -1.0f;

		FloatBuffer theMatrixBuffer = BufferUtils.createFloatBuffer( theMatrix.length );
		theMatrixBuffer.put(theMatrix);
		theMatrixBuffer.flip();

		glUseProgram(programShader);
		glUniformMatrix4(perspectiveMatrixUnif, false, theMatrixBuffer);
		glUseProgram(0);
	}

	public int getOffsetUniform()
	{
		return this.offsetUniform;
	}

	public int getProgram()
	{
		return this.programShader;
	}

	public void bind()
	{
		try
		{
			if ( programShader == 0 )
				createProgram();
			glUseProgram( programShader );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	public void addShader( int shaderType, String shaderFile )
	{
		shaderList.add( createShader( shaderType, shaderFile ) );
	}

	private int createShader( int shaderType, String shaderFile )
	{
		int shader = glCreateShader( shaderType );
		glShaderSource( shader, shaderFile );
		glCompileShader( shader );
		return shader;
	}

	private int initProgram( ArrayList<Integer> shaderList )
	{
		int program = glCreateProgram();

		for ( Integer shader : shaderList )
		{
			glAttachShader( program, shader );
		}

		glLinkProgram( program );

		int status = glGetProgrami( program, GL_LINK_STATUS );
		if ( status == GL_FALSE )
		{
			int infoLogLength = glGetProgrami( program, GL_INFO_LOG_LENGTH );

			String strInfoLog = glGetProgramInfoLog( program, infoLogLength );
			System.err.printf( "Linker failure: %s\n", strInfoLog );
		}

		for ( Integer shader : shaderList )
		{
			glDetachShader( program, shader );
		}

		return program;
	}

	public static String loadShader( String fileName )
	{
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader = null;
		final String INCLUDE_DIRECTIVE = "#include";

		try
		{
			shaderReader = new BufferedReader( new FileReader( "./res/shaders/" + fileName ) );
			String line;

			while ( ( line = shaderReader.readLine() ) != null )
			{
				if ( line.startsWith( INCLUDE_DIRECTIVE ) )
				{
					shaderSource.append( loadShader( line.substring( INCLUDE_DIRECTIVE.length() + 2, line.length() - 1 ) ) );
				}
				else
					shaderSource.append( line ).append( "\n" );
			}

			shaderReader.close();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			System.exit( 1 );
		}

		return shaderSource.toString();
	}
}