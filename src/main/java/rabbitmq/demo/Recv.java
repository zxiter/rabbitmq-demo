package rabbitmq.demo;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Recv
{
	private final static String QUEUE_NAME = "hello";

	public static void main(String[] args) throws IOException, TimeoutException
	{
		// 创建连接
		ConnectionFactory factory = new ConnectionFactory();
		// 设置MabbitMQ, 主机ip或者主机名
		factory.setHost("192.168.1.16");
		// 创建一个连接
		Connection connection = factory.newConnection();
		// 创建一个通道
		Channel channel = connection.createChannel();
		// 指定一个队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		// 创建队列消费者
		Consumer consumer = new DefaultConsumer(channel)
		{
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException
			{
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
			}
		};
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
}
