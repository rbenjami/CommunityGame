#version 120

varying vec3 color0;

uniform vec3 R_ambient;

void main()
{
	gl_FragColor = vec4(color0, 1) * vec4(R_ambient, 1);
}