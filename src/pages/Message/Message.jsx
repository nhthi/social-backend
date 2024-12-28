import { Avatar, Backdrop, CircularProgress, Grid, IconButton } from '@mui/material'
import React, { useEffect, useRef, useState } from 'react'
import WestIcon from '@mui/icons-material/West';
import SearchUser from '../../component/SearchUser/SearchUser';
import PopularUser from '../../component/HomeRight/PopularUser';
import CallIcon from '@mui/icons-material/Call';
import VideoCallIcon from '@mui/icons-material/VideoCall';
import AddPhotoAlternateIcon from '@mui/icons-material/AddPhotoAlternate';
import UserChatCard from './UserChatCard';
import ChatMessgae from './ChatMessgae';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { createMessage, getAllChats } from '../../Redux/Message/message.action';
import ChatBubbleOutlineIcon from '@mui/icons-material/ChatBubbleOutline';
import { uploadToCloundinary } from '../../utils/uploadToCloudniry';
import { create } from '@mui/material/styles/createTransitions';
import SendIcon from '@mui/icons-material/Send';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';


const Message = () => {
  const dispatch = useDispatch()
  const auth = useSelector(store => store.auth)
  const message = useSelector(store => store.message)

  const navigate = useNavigate();
  const [currentChat, setCurrentChat] = useState()
  const [messages, setMessages] = useState([])
  const [selectedImage, setSelectedImage] = useState()
  const [loading, setLoading] = useState(false)

  const [inputValue, setInputValue] = useState('');
  const userChat = currentChat ? (auth.user.id === currentChat.users[0].id ? currentChat.users[1] : currentChat?.users[0]) : null
  // su ly cuon tin nhan-------------------------------
  const messagesEndRef = useRef(null);
  useEffect(() => {
    if (messagesEndRef.current) {
      messagesEndRef.current.scrollTop = messagesEndRef.current.scrollHeight;
    }
  }, [messages]);
  // -----------------------------------------------------------

  useEffect(() => {
    dispatch(getAllChats())
  }, [])

  console.log('chats ----  ', message.chats);

  const handleSelectImage = async (e) => {
    setLoading(true)
    console.log('handle Select Image');
    const imgUrl = await uploadToCloundinary(e.target.files[0], 'image')
    setSelectedImage(imgUrl)
    setLoading(false)
  }
  const handleCreateMessage = (value) => {
    const chatId = currentChat.id
    const message = {
      content: value,
      image: selectedImage
    }
    dispatch(createMessage({ chatId, message, sendMessageToServer }))
  }
  useEffect(() => {
    setMessages([...messages, message.message])
  }, [message.message])

  const [stomClient, setStomClient] = useState(null)

  useEffect(() => {
    const sock = new SockJS('http://localhost:8080/ws')
    const stomp = Stomp.over(sock)
    setStomClient(stomp)

    stomp.connect({}, onConnect, onErr)
  }, [])
  const onConnect = (frame) => {
    console.log("websocket connected ...", frame);
    if (frame.headers) {
      console.log("Connected to server: ", frame.headers.server || "Server name not provided");
    } else {
      console.log("No headers received");
    }
  };
  const onErr = (err) => {
    console.log("error ...", err);
  }

  useEffect(() => {
    if (stomClient && auth.user && currentChat) {
        stomClient.subscribe(`/user/${currentChat.id}/private`, onMessageReice)
    }
  })

  const sendMessageToServer = (newMessage) => {
    if (stomClient && newMessage) {
      stomClient.send(`/app/chat/${currentChat?.id.toString()}`, {}, JSON.stringify(newMessage))
    }
  }

  const onMessageReice = (payload) => {
    const receivedMessage = JSON.parse(payload.body)
    console.log('message receive from websocket', receivedMessage);
    setMessages([...messages, receivedMessage])
  }
  return (
    <div>
      <Grid container className='h-screen overflow-y-hidden'>
        <Grid item xs={3} className='px-5'>
          <div className='flex h-full justify-between space-x-2'>
            <div className="w-full">
              <div className='flex space-x-4 items-center pt-5'>
                <IconButton onClick={() => {
                  navigate('/')
                }}>
                  <WestIcon />
                </IconButton>
                <h1 className='text-xl font-bold'>
                  Home
                </h1>
              </div>
              <div className='h-[83vh] flex flex-col'>
                <div className=''>
                  <SearchUser />
                </div>
                <div className='h-full space-y-4 mt-5 overflow-y-scroll hideScrollBar'>
                  {message.chats.map((item) => <div key={item.id} onClick={() => {
                    setCurrentChat(item)
                    setMessages(item.messages)
                  }}>
                    <UserChatCard chat={item} key={item.id} />
                  </div>
                  )}
                </div>
              </div>
            </div>
          </div>
        </Grid>
        <Grid item xs={9} className='h-full'>
          {currentChat ? <div className=''>
            <div className='flex justify-between items-center border-l p-7'>
              <div className='flex items-center space-x-3'>
                <Avatar src={userChat.avatar || "https://phunugioi.com/wp-content/uploads/2020/10/hinh-anh-yasuo-dep-sieu-pham.jpg"} />
                <p>{auth.user.id === currentChat.users[0].id ? currentChat.users[1].lastName + " " + currentChat.users[1].firstName :
                  currentChat.users[0].lastName + " " + currentChat.users[0].firstName}</p>
              </div>
              <div className='flex items-center space-x-3'>
                <IconButton>
                  <CallIcon />
                </IconButton>
                <IconButton>
                  <VideoCallIcon />
                </IconButton>
              </div>
            </div>
            <div ref={messagesEndRef} className='hideScrollBar overflow-y-scroll h-[76vh] px-2 space-y-5 py-5 flex flex-col'>
              {messages.map(item => <ChatMessgae key={item.id} message={item} />)}
              {/* scroll ---------------------------------------*/}
              {/* <div /> */}
            </div>

            <div className='sticky bottom-0 border-l bg-transparent pr-4 '>
              {selectedImage && <img src={selectedImage} alt="" className='w-[10rem] h-[10rem] object-cover px-2' />}
              <div className='py-5 flex items-center justify-center space-x-4'>
                <input
                  value={inputValue}
                  onChange={(e) => { setInputValue(e.target.value) }}
                  onKeyPress={(e) => {
                    if (e.key === 'Enter' && (e.target.value || selectedImage)) {
                      handleCreateMessage(e.target.value)
                      setSelectedImage('')
                      setInputValue('')
                    }
                  }}
                  type='text'
                  className='outline-none w-[90%] h-12 rounded-full px-5 bg-transparent border-[#3b4054] border'
                  placeholder='Type message ...'
                />
                <IconButton color='primary' onClick={() => {
                  if (inputValue || selectedImage) {
                    handleCreateMessage(inputValue)
                    setSelectedImage('')
                    setInputValue('')
                  }
                }}>
                  <SendIcon />
                </IconButton>
                <div>
                  <input
                    type='file'
                    accept='image/*'
                    onChange={handleSelectImage}
                    style={{ display: 'none' }}
                    id='image-input'
                  />

                  <label htmlFor='image-input'>
                    <IconButton color='primary' component='span'>
                      <AddPhotoAlternateIcon />
                    </IconButton>
                  </label>
                </div>
              </div>
            </div>
          </div> :
            <div className='h-full space-y-5 flex flex-col justify-center items-center'>
              <ChatBubbleOutlineIcon sx={{ fontSize: '15rem' }} />
              <p className='font-semibold text-xl'>
                No Chat Selected</p>
            </div>}
        </Grid>
      </Grid>
      {loading && <Backdrop
        sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }}
        open={loading}
      >
        <CircularProgress color="inherit" />
      </Backdrop>}
    </div>
  )
}

export default Message
