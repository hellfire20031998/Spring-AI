import React, { useState } from "react";

function ChatComponenet(){

    const[prompt,setPrompt]=useState('');
    const[chatResponse,setChatResponse]=useState('');

    const askAI=async()=>{
        try{
            const response= await fetch(`http://localhost:8080/ask-ai?prompt=`)
             const data= await response.text();
             setChatResponse(data)
         }catch(error){
             console.error("Error generating image:",error)
         };
    }
    return (
        <div>
            <h2>Talk to AI </h2>

            <input 
                type="text"
                value={prompt}
                onChange={(e)=>setPrompt(e.target.value)}
                placeholder="Enter a Prompt for AI"
                />
                <button onClick={askAI}>Ask AI</button>
                <div className="output">
                    <p>{chatResponse}</p>
                </div>
        </div>
    );
}

export default ChatComponenet;