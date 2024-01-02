
import './App.css'
import AddRoom from './components/room/AddRoom'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'
import Home from './components/home/Home'
import EditRoom from './components/room/EditRoom'
import ExistingRooms from './components/room/ExistingRooms'
import NavBar from './components/layout/NavBar'
import Footer from './components/layout/Footer'
import RoomListing from './components/room/RoomListing'
import Admin from './components/admin/Admin'

function App() {

  return (
    <>
      <main>
        <Router>
          <NavBar/>
          <Routes>
            <Route path='/' element={<Home/>}/>
            <Route path='/edit-room/:roomId' element={<EditRoom/>}/>
            <Route path='/existing-rooms' element={<ExistingRooms/>}/>
            <Route path='/add-room' element={<AddRoom/>}/>
            <Route path='/browse-all-rooms' element={<RoomListing/>}/>
            <Route path='/admin' element={<Admin/>}/>
          </Routes>
        </Router>
        <Footer/>
      </main>
    </>
  )
}

export default App
