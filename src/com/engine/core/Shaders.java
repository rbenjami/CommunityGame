package com.engine.core;

import com.engine.core.dimensions_helpers.Matrix4f;

import java.io.BufferedReader;
import java.io.FileReader;
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
	private int T_MVP;
	private int R_ambient;

	public Shaders( String name )
	{
		shaderList = new ArrayList<>();
		addShader( GL_FRAGMENT_SHADER, Shaders.loadShader( name + ".frag" ) );
		addShader( GL_VERTEX_SHADER, Shaders.loadShader( name + ".vert" ) );

		try
		{
			createProgram();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
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
		T_MVP = glGetUniformLocation( programShader, "T_MVP" );
		R_ambient = glGetUniformLocation( programShader, "R_ambient" );
	}

	public void updateUniforms( Transform transform, RenderEngine renderEngine )
	{
		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f MVPMatrix = renderEngine.getCamera().getViewProjection().mul( worldMatrix );

		glUniform3f( R_ambient, 0.1f, 0.1f, 0.1f );
		glUniformMatrix4( T_MVP, true, MVPMatrix.toFloatBuffer() );
	}

	public void bind()
	{
		glUseProgram( programShader );
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

	public int getProgram()
	{
		return this.programShader;
	}


	/**
	 * SETTER
	 */
}