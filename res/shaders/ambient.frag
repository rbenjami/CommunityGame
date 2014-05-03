#version 120

uniform vec3 R_ambient;

void main()
{
	gl_FragColor = vec4(R_ambient, 1);
}