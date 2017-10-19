package services

import com.yukihirai0505.iService.responses.{ProfileUserData, Tag}
import com.yukihirai0505.iService.services.{MediaService, UserService}

import scala.concurrent.{ExecutionContextExecutor, Future}

class InstagramService(implicit ec: ExecutionContextExecutor) {

  def getUserInfo(accountName: String): Future[Option[ProfileUserData]] = {
    UserService.getUserInfo(accountName).flatMap {
      case Right(data) => Future successful Some(data)
      case Left(e) => Future successful None
    }
  }

  def getTagInfo(tagName: String): Future[Option[Tag]] = {
    MediaService.getPosts(tagName).flatMap {
      case Right(data) => Future successful Some(data)
      case Left(e) => Future successful None
    }
  }
}