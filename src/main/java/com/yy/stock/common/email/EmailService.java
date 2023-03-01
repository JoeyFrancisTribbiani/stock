package com.yy.stock.common.email;

import org.jetbrains.annotations.NotNull;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailService {
    /**
     * 接收IMAP邮件
     * 筛选条件
     * 　　JavaMail在javax.mail.search包中定义了一个用于创建搜索条件的SearchTerm类，应用程序创建SearchTerm类的实例对象后，就可以调用Folder.Search(SearchTerm st)方法搜索邮件夹中符合搜索条件的所有邮件。SearchTerm是一个抽象类，JavaMail提供了22个实现子类以帮助应用程序创建不同的搜索条件，这22个类可分为两大类型，如下所示：
     * 1、用于创建逻辑组合关系的类
     * AND条件（AndTerm类）
     * OR条件（OrTerm类）
     * NOT条件（NotTerm类）
     * Comparison条件（ComparisonTerm类）
     * 2、用于创建具体搜索条件的类
     * DATE条件（SentDateTerm、ReceivedDateTerm类）
     * CONTENT条件（BodyTerm类）
     * HEADER条件（FromStringTerm、RecipientStringTerm、SubjectTerm类等）
     * 下面通过实现来说明以上类的用法及含义：
     * 1、搜索发件人为“智联招聘“，而且邮件正文包含“Java工程师“的所有邮件
     * SearchTerm andTerm = new AndTerm( new FromStringTerm("智联招聘"), new BodyTerm("java工程师"));
     * Message[] messages = folder.search(andTerm);
     * 2、搜索发件人为“智联招聘“或主题包含“最新职位信息“的所有邮件
     * SearchTerm orTerm = new OrTerm( new FromStringTerm("智联招聘"), new SubjectTerm("最新职位信息"));
     * Message[] messages = folder.search(orTerm);
     * 3、搜索周一到今天收到的的所有邮件
     * Calendar calendar = Calendar.getInstance();
     * calendar.set(Calendar.DAY_OF_WEEK, calendar.get(Calendar.DAY_OF_WEEK - (Calendar.DAY_OF_WEEK - 1)) - 1);
     * Date mondayDate = calendar.getTime();
     * SearchTerm comparisonTermGe = new SentDateTerm(ComparisonTerm.GE, mondayDate);
     * SearchTerm comparisonTermLe = new SentDateTerm(ComparisonTerm.LE, new Date());
     * SearchTerm comparisonAndTerm = new AndTerm(comparisonTermGe, comparisonTermLe);
     * Message[] messages = folder.search(comparisonAndTerm);
     * 4、搜索大于或等100KB的所有邮件
     * int mailSize = 1024 * 100; SearchTerm intComparisonTerm = new SizeTerm(IntegerComparisonTerm.GE, mailSize);
     * Message[] messages = folder.search(intComparisonTerm);
     * ComparisonTerm类常用于日期和数字比较中，它使用六个常量EQ（＝）、GE（>=）、GT（>）、LE（<=）、LT（<）、NE（!=）来表示六种不同的比较操作。
     * //如果需要在取得邮件数后将邮件置为已读则这里需要使用READ_WRITE,否则READ_ONLY就可以
     * inbox.open(Folder.READ_WRITE);
     * // Message messages[] = inbox.getMessages(); //获取所有邮件
     * //建立搜索条件FlagTerm，这里FlagTerm继承自SearchTerm，也就是说除了获取未读邮
     * //件的条件还有很多其他条件同样继承了SearchTerm的条件类，像根据发件人，主题搜索等，
     * // 还有复杂的逻辑搜索类似：
     * //
     * //    SearchTerm orTerm = new OrTerm(
     * //            new FromStringTerm(from),
     * //            new SubjectTerm(subject)
     * //            );
     * //
     * FlagTerm ft =
     * new FlagTerm(new Flags(Flags.Flag.SEEN), false); //false代表未读，true代表已读
     * /**
     * * Flag 类型列举如下
     * * Flags.Flag.ANSWERED 邮件回复标记，标识邮件是否已回复。
     * * Flags.Flag.DELETED 邮件删除标记，标识邮件是否需要删除。
     * * Flags.Flag.DRAFT 草稿邮件标记，标识邮件是否为草稿。
     * * Flags.Flag.FLAGGED 表示邮件是否为回收站中的邮件。
     * * Flags.Flag.RECENT 新邮件标记，表示邮件是否为新邮件。
     * * Flags.Flag.SEEN 邮件阅读标记，标识邮件是否已被阅读。
     * * Flags.Flag.USER 底层系统是否支持用户自定义标记，只读。
     */
//    public Object reciveIMAPmail(String username, String password, SearchTerm searchTerm) throws MessagingException, IOException;
    public @NotNull String getEmailVerifyCode(String email, String password) throws MessagingException, IOException;
}
