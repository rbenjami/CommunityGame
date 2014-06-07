#version 120
#include "lighting.glh"

in vec4 color0;
in vec3 normal0;
in vec3 worldPos0;

uniform SpotLight R_spotLight;
void main()
{
	gl_FragColor = color0 * CalcSpotLight(R_spotLight, normalize(normal0), worldPos0);
}
