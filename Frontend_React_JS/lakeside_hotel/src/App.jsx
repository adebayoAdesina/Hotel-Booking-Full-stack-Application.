import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import AddRoom from './components/room/AddRoom'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'
import Home from './components/home/Home'
import EditRoom from './components/room/EditRoom'
import ExistingRooms from './components/room/ExistingRooms'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <main>
        <Router>
          <Routes>
            <Route path='/' element={<Home/>}/>
            <Route path='/edit-room/:roomId' element={<EditRoom/>}/>
            <Route path='/existing-rooms' element={<ExistingRooms/>}/>
          </Routes>
        </Router>
      </main>
    </>
  )
}

export default App
