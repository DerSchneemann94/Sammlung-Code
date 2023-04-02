import React from 'react';
import { useDispatch, useSelector } from '../..';
import { requestUserEdit } from '../../api/api';
import { selectUser } from '../../selectors/userSelectors';
import { userSetImage } from '../../slices/userSlice';
import { BoldTitle, UserProfileImage } from '../constants/globalStyles';
import {
  UserEditContent,
  UserEditProfilePictureInput,
  UserEditProfilePictureText,
  UserEditProfilePictureWrapper,
  UserEditWrapper,
} from './UserEdit.style';
import toast from 'react-hot-toast';

function arrayBufferToBase64(buffer: ArrayBuffer) {
  let binary = '';
  const bytes = new Uint8Array(buffer);
  const len = bytes.byteLength;
  for (let i = 0; i < len; i++) {
    binary += String.fromCharCode(bytes[i]);
  }
  return window.btoa(binary);
}

export function UserEdit(): JSX.Element {
  const dispatch = useDispatch();
  const user = useSelector(selectUser);

  async function imageSelected(event: React.ChangeEvent<HTMLInputElement>) {
    if (!event.target.files || event.target.files?.length <= 0) return;
    const image = `data:image/png;base64, ${arrayBufferToBase64(
      await event.target.files[0].arrayBuffer(),
    )}`;
    try {
      await requestUserEdit(image);
    } catch (err) {
      console.error(err);
      toast.error('' + err);
      return;
    }

    dispatch(userSetImage(image));
  }
  return (
    <UserEditWrapper>
      <UserEditContent>
        <BoldTitle>EDIT</BoldTitle>
        <UserEditProfilePictureWrapper>
          <UserEditProfilePictureText>Profilbild</UserEditProfilePictureText>
          <UserProfileImage src={user.image} alt="preview" />
          <UserEditProfilePictureInput
            type="file"
            accept="image/png, image/jpeg"
            onChange={imageSelected}
          />
        </UserEditProfilePictureWrapper>
      </UserEditContent>
    </UserEditWrapper>
  );
}
