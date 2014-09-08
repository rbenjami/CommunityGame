#version 120

varying vec4 color0;

uniform vec3 R_ambient;

void main()
{
	gl_FragColor = color0 * vec4(R_ambient, 1);
}