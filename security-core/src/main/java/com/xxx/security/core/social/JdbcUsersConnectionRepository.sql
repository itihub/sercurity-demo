create table UserConnection (
	userId varchar(255) NOT NULL COMMENT'用户唯一标识',
	providerId varchar(255) NOT NULL COMMENT'服务提供商唯一标识',
	providerUserId varchar(255)  COMMENT'openid 第三方服务提供商用户唯一标识',
	rank int not null COMMENT'等级',
	displayName varchar(255) COMMENT'用户昵称',
	profileUrl varchar(512) COMMENT'用户个人主页url',
	imageUrl varchar(512) COMMENT'用户头像url',
	accessToken varchar(512) NOT NULL COMMENT'用户访问令牌',
	secret varchar(512) COMMENT'加密',
	refreshToken varchar(512) COMMENT'刷新令牌',
	expireTime bigint COMMENT'过期时间',
	primary key (userId, providerId, providerUserId)) COMMENT'Spring social 用户连接表';
create unique index UserConnectionRank on UserConnection(userId, providerId, rank);