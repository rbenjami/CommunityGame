#version 410

layout(location = 0) in vec4 position;
layout(location = 1) in vec4 color;

smooth out vec4 theColor;

uniform mat4 T_MVP;

void main()
{
	gl_Position = T_MVP * position;
	theColor = color;
}