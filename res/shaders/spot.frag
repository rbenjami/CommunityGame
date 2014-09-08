#version 120
#include "lighting.glh"

varying vec4 color0;
varying vec3 normal0;
varying vec3 worldPos0;

uniform SpotLight R_spotLight;

void main()
{
	gl_FragColor = color0 * CalcSpotLight(R_spotLight, normalize(normal0), worldPos0);
}
