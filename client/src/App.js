import logo from './logo.svg';
import './App.css';
import React,{act, useState} from 'react';
import ImageGenerator from './components/ImageGenerator';
import ChatComponenet from './components/Chat';
import RecipeGenerator from './components/RecipeGenerator';

function App() {
  const [activeTab, setActiveTab]=useState('image-generator');


  const handleTabChange=(tab)=>{
    // alert(tab)
    setActiveTab(tab);
  };

  return (
    <div className="App">
      <button className={activeTab=='image-generator'? 'active' : ''}
       onClick={()=>handleTabChange('image-generator')}>
        Image Generator
        </button>
      <button className={activeTab=='chat'? 'active' : ''}
      onClick={()=>handleTabChange('chat')}>
        Chat
        </button>
      <button className={activeTab=='recipe-generator'? 'active' : ''} 
      onClick={()=>handleTabChange('recipe-generator')}>
        Recipe Generator
        </button>

        <div>
          {activeTab==='image-generator' && <ImageGenerator/>}
          {activeTab==='chat' && <ChatComponenet/>}
          {activeTab==='recipe-generator' && <RecipeGenerator/>}
        </div>
    </div>
  );
}

export default App;
