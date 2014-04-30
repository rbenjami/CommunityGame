#version 330

smooth in vec4 theColor;

uniform vec3 R_ambient;

void main()
{
	gl_FragColor = vec4(R_ambient, 1) * theColor;
}
