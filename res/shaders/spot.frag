#version 130
#include "lighting.glh"

in vec4 color0;
in vec3 normal0;
in vec3 worldPos0;

uniform SpotLight R_spotLight;

out vec4 fragColor;

void main()
{
	fragColor = color0 * CalcSpotLight(R_spotLight, normalize(normal0), worldPos0);
}
