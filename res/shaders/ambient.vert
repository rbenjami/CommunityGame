#version 120

attribute vec4 position;

uniform mat4 T_MVP;

void main()
{
    gl_Position = T_MVP * position;
}
