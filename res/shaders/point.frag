#version 120
#include "lighting.glh"

varying vec4 color0;
varying vec3 normal0;
varying vec3 worldPos0;

uniform PointLight R_pointLight;

void main()
{
    gl_FragColor = color0 * CalcPointLight(R_pointLight, normalize(normal0), worldPos0);
}
