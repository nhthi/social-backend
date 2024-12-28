import * as React from 'react';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import { useFormik } from 'formik';
import { useDispatch, useSelector } from 'react-redux';
import { updateProfileAction } from '../../Redux/Auth/auth.action';
import { Avatar, Backdrop, CircularProgress, IconButton, TextField } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import ImageIcon from '@mui/icons-material/Image';
import VideocamIcon from '@mui/icons-material/Videocam';
import { useState } from 'react';
import { uploadToCloundinary } from '../../utils/uploadToCloudniry';
import { createPostAction } from '../../Redux/Post/post.action';


const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 600,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 4,
    outline: 'none',
    overFlow: 'scroll-y',
    borderRadius: '.6rem'
};

export const CreatePostModal = ({ open, handleClose }) => {
    const dispatch = useDispatch();
    const formik = useFormik(
        {
            initialValues: {
                caption: "",
                image: "",
                video: ""
            },
            onSubmit: (values) => {
                console.log("formik values :", values);
                if (values.caption || values.image || values.video) {
                    dispatch(createPostAction(values))
                    handleClose()
                }
            }
        }
    )
    const [isLoading, setIsLoading] = useState(false)
    const [selectImage, setSelectImage] = useState('https://getwallpapers.com/wallpaper/full/8/8/e/861137-lee-sin-wallpapers-1920x1080-for-1080p.jpg')
    const [selectVideo, setSelectVideo] = useState(null)
    const [openModal, setOpenModal] = useState(open)

    const handleSelectImage = async (event) => {
        setIsLoading(true)
        

        const imageUrl = await uploadToCloundinary(event.target.files[0], 'image')
        setSelectImage(imageUrl)
        setIsLoading(false)
        formik.setFieldValue("image", imageUrl)
    }
    const handleSelectVideo = async (event) => {
        setIsLoading(true)
        const videoUrl = await uploadToCloundinary(event.target.files[0], 'video')
        setSelectVideo(videoUrl)
        setIsLoading(false)
        formik.setFieldValue("video", videoUrl)
    }


    return (
        <div>
            <Modal
                open={openModal}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box sx={style}>
                    <form onSubmit={formik.handleSubmit}>
                        <div>
                            <div className='flex space-x-4 items-center'>
                                <Avatar />
                                <div >
                                    <p className='font-bold text-lg'>Nguyen Thinh</p>
                                    <p className='text-sm'>@thinhnguyen</p>
                                </div>
                            </div>
                            <textarea
                                className='w-full outline-none mt-5 border border-slate-300 p-2 bg-transparent'
                                placeholder='Write caption ...'
                                name="caption"
                                id="caption"
                                rows='4'
                                value={formik.values.caption}
                                onChange={formik.handleChange}
                            >

                            </textarea>
                            <div className='flex space-x-5 items-center mt-5'>
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
                                            <ImageIcon />
                                        </IconButton>
                                    </label>
                                    <span>Image</span>
                                </div>
                                <div>
                                    <input
                                        type='file'
                                        accept='video/*'
                                        onChange={handleSelectVideo}
                                        style={{ display: 'none' }}
                                        id='video-input'
                                    />
                                    <label htmlFor='video-input'>
                                        <IconButton color='primary' component='span'>
                                            <VideocamIcon />
                                        </IconButton>
                                    </label>
                                    <span>Video</span>
                                </div>
                            </div>

                            {selectImage &&
                                <div>
                                    <img className='h-[10rem] w-full object-contain' src={selectImage} />
                                </div>}
                            {/* {selectVideo &&
                                <div>
                                    <video controls className='h-[10rem] w-full object-contain' src={selectImage} />
                                </div>} */}
                            <div className='w-full flex justify-end items-center'>
                                <Button variant='contained' type='submit' sx={{ borderRadius: '1.5rem' }}>
                                    Post
                                </Button>
                            </div>
                        </div>
                        {isLoading && <Backdrop
                            sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }}
                            open={open}
                            onClick={handleClose}
                        >
                            <CircularProgress color="inherit" />
                        </Backdrop>}
                    </form>
                </Box>

            </Modal>

        </div>
    );
}