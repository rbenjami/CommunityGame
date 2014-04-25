package com.engine.core;

import com.engine.Window;

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
	private int    modelToCameraMatrixUnif;
	private int    cameraToClipMatrixUnif;
	private int    baseColorUnif;
	private Camera cam;

	public Shaders()
	{
		shaderList = new ArrayList<>();
		addShader( GL_FRAGMENT_SHADER, Shaders.loadShader( "ColorMultUniform.frag" ) );
		addShader( GL_VERTEX_SHADER, Shaders.loadShader( "PosColorLocalTransform.vert" ) );
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
		loadUniform();
	}

	public void loadUniform()
	{
		modelToCameraMatrixUnif = glGetUniformLocation( programShader, "modelToCameraMatrix" );
		cameraToClipMatrixUnif = glGetUniformLocation( programShader, "cameraToClipMatrix" );
		baseColorUnif = glGetUniformLocation( programShader, "baseColor" );

		FloatBuffer cameraToClipMatrix = new Matrix4f().initPerspective( (float) Math.toRadians( 70.0f ), (float) Window.getWidth() / (float) Window.getHeight(), 0.5f, 10 ).toFloatBuffer();

		glUseProgram( programShader );
		glUniformMatrix4( cameraToClipMatrixUnif, false, cameraToClipMatrix );
		glUseProgram( 0 );
	}

	public void bind()
	{
		glUseProgram( programShader );
	}

	public void unbind()
	{
		glUseProgram( 0 );
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


	/**
	 * GETTER
	 */
	public int getModelToCameraMatrixUnif()
	{
		return modelToCameraMatrixUnif;
	}

	public int getCameraToClipMatrixUnif()
	{
		return cameraToClipMatrixUnif;
	}

	public int getBaseColorUnif()
	{
		return baseColorUnif;
	}

	public int getProgram()
	{
		return this.programShader;
	}

	public Camera getCamera()
	{
		return cam;
	}


	/**
	 * SETTER
	 */
	public void setCamera( Camera cam )
	{
		this.cam = cam;
	}
}