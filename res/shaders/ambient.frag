#version 130

in vec4 color0;

uniform vec3 R_ambient;

out vec4 fragColor;

void main()
{
	fragColor = color0 * vec4(R_ambient, 1);
}