#version 120

attribute vec3 position;
attribute vec3 color;
attribute vec3 normal;

varying vec3 color0;
varying vec3 normal0;
varying vec3 worldPos0;

uniform mat4 T_model;
uniform mat4 T_MVP;

void main()
{
    gl_Position = T_MVP * vec4(position, 1.0);
    color0 = color;
    normal0 = (T_model * vec4(normal, 0.0)).xyz;
    worldPos0 = (T_model * vec4(position, 1.0)).xyz;
}
