package services

import com.typesafe.scalalogging.LazyLogging
import com.yukihirai0505.iService.responses._
import com.yukihirai0505.iService.services.{CommentService, MediaService, UserService}

import scala.concurrent.{ExecutionContextExecutor, Future}

class InstagramService(implicit ec: ExecutionContextExecutor) extends LazyLogging {

  def getUserInfo(accountName: String): Future[Option[ProfileUserData]] = {
    toOptional(UserService.getUserInfo(accountName))
  }

  def getUserPostsPaging(userId: String, afterCode: String = ""): Future[Option[UserPostQuery]] = {
    toOptional(UserService.getPostsPaging(userId, size = 200, afterCode))
  }

  def getTagInfo(tagName: String): Future[Option[Tag]] = {
    toOptional(MediaService.getPosts(tagName))
  }

  def getTagPostsPaging(tagName: String, afterCode: String = ""): Future[Option[MediaQuery]] = {
    toOptional(MediaService.getPostsPaging(tagName, size = 200, afterCode))
  }

  def getMediaInfo(shortcode: String): Future[Option[PostPageGraphql]] = {
    toOptional(MediaService.getPostInfo(shortcode))
  }

  def getCommentPaging(shortcode: String, afterCode: String = ""): Future[Option[MediaCommentQuery]] = {
    toOptional(CommentService.getCommentsPaging(shortcode, size = 200, afterCode))
  }

  private def toOptional[T](either: Future[Either[Throwable, T]]) = {
    either.flatMap {
      case Right(data) => Future successful Some(data)
      case Left(e) =>
        logger.warn("Error at toOptional: ", e)
        Future successful None
    }
  }
}