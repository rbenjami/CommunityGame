#version 120
#include "lighting.glh"

varying vec3 color0;
varying vec3 normal0;
varying vec3 worldPos0;

uniform DirectionalLight R_directionalLight;

void main()
{
    gl_FragColor = vec4(color0, 1) * CalcDirectionalLight(R_directionalLight, normalize(normal0), worldPos0);
}