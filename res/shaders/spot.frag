#version 120
#include "lighting.glh"

varying vec3 normal0;
varying vec3 worldPos0;

uniform SpotLight R_spotLight;

void main()
{
    gl_FragColor = CalcSpotLight(R_spotLight, normalize(normal0), worldPos0);
}
