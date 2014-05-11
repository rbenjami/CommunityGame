#version 130

in vec3 position;
in vec3 color;
in vec3 normal;
in float transparency;

out vec4 color0;
out vec3 normal0;
out vec3 worldPos0;

uniform mat4 T_model;
uniform mat4 T_MVP;

void main()
{
    gl_Position = T_MVP * vec4(position, 1.0);
    color0 = vec4(color, transparency);
    normal0 = (T_model * vec4(normal, 0.0)).xyz;
    worldPos0 = (T_model * vec4(position, 1.0)).xyz;
}
