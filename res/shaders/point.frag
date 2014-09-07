#version 130
#include "lighting.glh"

in vec4 color0;
in vec3 normal0;
in vec3 worldPos0;

uniform PointLight R_pointLight;

out vec4 fragColor;

void main()
{
    fragColor = color0 * CalcPointLight(R_pointLight, normalize(normal0), worldPos0);
}
