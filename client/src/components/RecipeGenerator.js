import React, { useState } from "react";

function RecipeGenerator(){
    const [ingredients , setIngredients]=useState('');
    const [cuisine , setCuisine]=useState('');
    const [dietaryRestrictions , setDietaryRestricitons]=useState('');
    const[recipe,setRecipe]=useState('');

    const createRecipe = async () =>{
        try{
            const response= await fetch(`http://localhost:8080/recipe-creator?ingredients=${ingredients}&dietaryRestrictions=${dietaryRestrictions}&cuisine=${cuisine}`)
             const data= await response.text();
             setRecipe(data)
         }catch(error){
             console.error("Error generating recipe:",error)
         };
    }

    return (
       <div>
         <h2>Generate Recipe</h2>
         <input
            type="text"
            value={ingredients}
            onChange={(e)=>setIngredients(e.target.value)}
            placeholder="Enter ingredients(comma separated)"/>

<input
            type="text"
            value={cuisine}
            onChange={(e)=>setCuisine(e.target.value)}
            placeholder="Enter Cuisine(comma separated)"/>

<input
            type="text"
            value={dietaryRestrictions}
            onChange={(e)=>setDietaryRestricitons(e.target.value)}
            placeholder="Enter dietaryRestrictions(comma separated)"/>

            <button onClick={createRecipe}>Create Recipe</button>

            <div className="output">
                <pre className="recipe-text">{recipe}</pre>
            </div>
       </div>
    );
}

export default RecipeGenerator;