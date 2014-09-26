package com.engine.core.helpers.quadtree;


import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe implémente la structure QuadTree
 *
 * @author Xavier Philippeau
 */
public class QuadTree
{

	// organisation des noeuds enfant:
	//  _________
	// |    |    |
	// | NW | NE |
	// |____|____|
	// |    |    |
	// | SW | SE |
	// |____|____|

	// types de noeuds
	static final int TYPE_ROOT = -1, TYPE_NW = 0, TYPE_NE = 1, TYPE_SW = 2, TYPE_SE = 3;

	// informations utilisées pour la navigation dans le QuadTree

	// types des ancetres communs
	private static final int[] rightAncestors  = new int[]{ TYPE_NW, TYPE_SW };
	private static final int[] leftAncestors   = new int[]{ TYPE_NE, TYPE_SE };
	private static final int[] bottomAncestors = new int[]{ TYPE_NW, TYPE_NE };
	private static final int[] topAncestors    = new int[]{ TYPE_SW, TYPE_SE };

	// tableau de symetrie pour l'inversion des chemins
	private static final int[] horizontalReverter = new int[]{ TYPE_NE, TYPE_NW, TYPE_SE, TYPE_SW };
	private static final int[] verticalReverter   = new int[]{ TYPE_SW, TYPE_SE, TYPE_NW, TYPE_NE };

	// types des enfants
	private static final int[] leftNodes   = new int[]{ TYPE_NW, TYPE_SW };
	private static final int[] rightNodes  = new int[]{ TYPE_NE, TYPE_SE };
	private static final int[] topNodes    = new int[]{ TYPE_NW, TYPE_NE };
	private static final int[] bottomNodes = new int[]{ TYPE_SW, TYPE_SE };
	private static final int[] AllNodes    = new int[]{ TYPE_NW, TYPE_NE, TYPE_SW, TYPE_SE };

	// profondeur maximum de l'arbre
	public static int MAXDEPTH = 0;

	// composants du noeud
	public int      type;
	public int      depth;
	public QuadTree parent;
	public QuadTree[] children = null;

	public Object object; // champ libre (donnée utilisateur)

	/**
	 * constructeur public (racine de l'arbre)
	 */
	public QuadTree( Object o )
	{
		this.parent = null;
		this.type = TYPE_ROOT;
		this.depth = 0;
		this.object = o;
	}

	// constructeur privé (utilisé lors du split)
	private QuadTree( QuadTree parent, int type, Object o )
	{
		this.parent = parent;
		this.type = type;
		this.depth = parent.depth + 1;
		this.object = o;
		if ( depth > MAXDEPTH ) MAXDEPTH = depth;
	}

	// retourne le noeud voisin d'un noeud (algorithme générique)
	private static QuadTree sibling( QuadTree node, int[] ancestortype, int[] reverter )
	{
		int[] path = new int[MAXDEPTH + 1];
		int pathlength = 0;

		// recherche du plus proche ancetre commun
		QuadTree ancestor = node;
		while ( true )
		{
			if ( ancestor.type == -1 ) return null; // no common ancestor -> exit
			path[pathlength] = ancestor.type;
			pathlength++;
			if ( ancestor.type == ancestortype[0] )
			{
				ancestor = ancestor.parent;
				break;
			}
			if ( ancestor.type == ancestortype[1] )
			{
				ancestor = ancestor.parent;
				break;
			}
			ancestor = ancestor.parent;
		}

		// parcours de l'arbre en utilsant le chemin symetrique
		QuadTree cursor = ancestor, next = null;
		for ( int i = pathlength - 1; i >= 0; i-- )
		{
			if ( cursor.children == null ) break;
			next = cursor.children[reverter[path[i]]];
			if ( next == null ) break;
			cursor = next;
		}
		return cursor;
	}

	// parcours reccursif des enfants. Helper pour la methode childrens()
	private void childrens_atom( List<QuadTree> results, QuadTree node, int[] finalTypes )
	{
		if ( node == null ) return;
		if ( node.children == null )
		{
			results.add( node );
			return;
		}
		for ( int type : finalTypes )
		{
			childrens_atom( results, node.children[type], finalTypes );
		}
	}

	// retourne la liste des feuilles accessibles à partir d'un noeud

	private List<QuadTree> childrens( QuadTree node, int[] finaltypes )
	{
		List<QuadTree> results = new ArrayList<QuadTree>();
		childrens_atom( results, node, finaltypes );
		return results;
	}

	// ----------------------------------------------------------------------------------

	/**
	 * split un noeud, i.e. création des 4 enfants (ordre=NW,NE,SW,SE)
	 */
	public void split( Object... objects )
	{
		if ( children != null ) return;
		children = new QuadTree[4];
		children[TYPE_NW] = new QuadTree( this, TYPE_NW, null );
		children[TYPE_NE] = new QuadTree( this, TYPE_NE, null );
		children[TYPE_SW] = new QuadTree( this, TYPE_SW, null );
		children[TYPE_SE] = new QuadTree( this, TYPE_SE, null );

		if ( objects.length >= 1 ) children[TYPE_NW].object = objects[0];
		if ( objects.length >= 2 ) children[TYPE_NE].object = objects[1];
		if ( objects.length >= 3 ) children[TYPE_SW].object = objects[2];
		if ( objects.length >= 4 ) children[TYPE_SE].object = objects[3];
	}

	// ----------------------------------------------------------------------------------

	/**
	 * retourne le noeud représentant la case de droite
	 */
	public QuadTree getRightSibling()
	{
		return sibling( this, QuadTree.rightAncestors, QuadTree.horizontalReverter );
	}

	/**
	 * retourne le noeud représentant la case de gauche
	 */
	public QuadTree getLeftSibling()
	{
		return sibling( this, QuadTree.leftAncestors, QuadTree.horizontalReverter );
	}

	/**
	 * retourne le noeud représentant la case du dessus
	 */
	public QuadTree getTopSibling()
	{
		return sibling( this, QuadTree.topAncestors, QuadTree.verticalReverter );
	}

	/**
	 * retourne le noeud représentant la case du dessous
	 */
	public QuadTree getBottomSibling()
	{
		return sibling( this, QuadTree.bottomAncestors, QuadTree.verticalReverter );
	}

	// ----------------------------------------------------------------------------------

	/**
	 * retourne toutes les feuilles de type: gauche
	 */
	public List<QuadTree> getLeftChildren()
	{
		return childrens( this, QuadTree.leftNodes );
	}

	/**
	 * retourne toutes les feuilles de type: droite
	 */
	public List<QuadTree> getRightChildren()
	{
		return childrens( this, QuadTree.rightNodes );
	}

	/**
	 * retourne toutes les feuilles de type: haut
	 */
	public List<QuadTree> getTopChildren()
	{
		return childrens( this, QuadTree.topNodes );
	}

	/**
	 * retourne toutes les feuilles de type: bas
	 */
	public List<QuadTree> getBottomChildren()
	{
		return childrens( this, QuadTree.bottomNodes );
	}

	/**
	 * retourne toutes les feuilles
	 */
	public List<QuadTree> getLeaves()
	{
		return childrens( this, QuadTree.AllNodes );
	}

	// ----------------------------------------------------------------------------------

	/**
	 * retourne les noeuds représentant les cases voisines à gauche
	 */
	public List<QuadTree> getRightNeighbors()
	{
		QuadTree sibling = this.getRightSibling();
		if ( sibling == null ) return new ArrayList<QuadTree>();
		return sibling.getLeftChildren();
	}

	/**
	 * retourne les noeuds représentant les cases voisines à droite
	 */
	public List<QuadTree> getLeftNeighbors()
	{
		QuadTree sibling = this.getLeftSibling();
		if ( sibling == null ) return new ArrayList<QuadTree>();
		return sibling.getRightChildren();
	}

	/**
	 * retourne les noeuds représentant les cases voisines au dessus
	 */
	public List<QuadTree> getTopNeighbors()
	{
		QuadTree sibling = this.getTopSibling();
		if ( sibling == null ) return new ArrayList<QuadTree>();
		return sibling.getBottomChildren();
	}

	/**
	 * retourne les noeuds représentant les cases voisines en dessous
	 */
	public List<QuadTree> getBottomNeighbors()
	{
		QuadTree sibling = this.getBottomSibling();
		if ( sibling == null ) return new ArrayList<QuadTree>();
		return sibling.getTopChildren();
	}
}