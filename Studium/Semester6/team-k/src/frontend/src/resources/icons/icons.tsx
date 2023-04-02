import React from 'react';
import IconUpVoteBMP from './upVote.bmp';
import IconDownVoteBMP from './downVote.bmp';
import styled from 'styled-components';

export const IconArrowImage = styled.img`
  width: 6.25rem;
  height: 6.25rem;
`;

type IconParams = {
  onClick?: React.MouseEventHandler<HTMLImageElement>;
};

export function IconUpVote({ onClick }: IconParams): JSX.Element {
  return <IconArrowImage src={IconUpVoteBMP} onClick={onClick} />;
}

export function IconDownVote({ onClick }: IconParams): JSX.Element {
  return <IconArrowImage src={IconDownVoteBMP} onClick={onClick} />;
}
