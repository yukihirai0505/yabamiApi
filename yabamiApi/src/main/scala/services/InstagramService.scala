package services

import com.typesafe.scalalogging.LazyLogging
import com.yukihirai0505.iService.responses._
import com.yukihirai0505.iService.services.{CommentService, LikeService, MediaService, UserService}

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

  def getLikePaging(shortcode: String, afterCode: String = ""): Future[Option[LikeQueryShortcodeMedia]] = {
    toOptional(LikeService.getLikePaging(shortcode, size = 200, afterCode))
  }

  def getRecommendHashTag(tagName: String): Future[Seq[String]] = {
    getTagInfo(tagName).flatMap {
      case Some(posts) =>
        def extractHashTag(caption: String): Seq[String] = {
          val pattern = """#[a-zA-Z0-9_\u3041-\u3094\u3099-\u309C\u30A1-\u30FA\u3400-\uD7FF\uFF10-\uFF19\uFF20-\uFF3A\uFF41-\uFF5A\uFF66-\uFF9E|\w-ー]*""".r
          pattern.findAllIn(caption).map(_.replace("#", "")).toSeq
        }

        val hashTags = posts.topPosts.nodes.flatMap(n => extractHashTag(n.caption.getOrElse(""))).distinct.filter(!_.equals(tagName))
        Future successful hashTags
      case _ => Future successful Seq.empty
    }
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