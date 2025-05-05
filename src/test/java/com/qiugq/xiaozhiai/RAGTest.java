package com.qiugq.xiaozhiai;

import dev.langchain4j.community.model.dashscope.QwenTokenizer;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.parser.apache.poi.ApachePoiDocumentParser;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.onnx.HuggingFaceTokenizer;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.List;

/**
 * 检索增强测试类
 */
@SpringBootTest
public class RAGTest {

	/**
	 * 解析文档，默认解析器只能解析纯文本文档
	 */
	@Test
	public void testReadDocument() {

		String directoryPath = "src/test/java/com/qiugq/xiaozhiai/knowledge";

		// 使用FileSystemDocumentLoader读取指定目录下的知识库文档
		// 并使用默认的文档解析器TextDocumentParser对文档进行解析
		Document document1 = FileSystemDocumentLoader.loadDocument(directoryPath + "/测试.txt");
		System.out.println(document1.text());

		// 加载单个文档，使用默认加载器的话，效果与上面一致
		Document document2 = FileSystemDocumentLoader.loadDocument(directoryPath + "/file.txt", new TextDocumentParser());
		System.out.println(document2.text());

		// 从一个目录中加载所有文档
		List<Document> documents3 = FileSystemDocumentLoader.loadDocuments(directoryPath, new TextDocumentParser());
		documents3.forEach(document -> {
			System.out.println("=========================");
			System.out.println(document.metadata());
			System.out.println(document.text());
		});

		// 从一个目录中加载所有的.txt文档
		PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:*.txt");
		List<Document> documents4 = FileSystemDocumentLoader.loadDocuments(directoryPath, pathMatcher, new TextDocumentParser());
		documents4.forEach(document -> {
			System.out.println("=========================");
			System.out.println(document.metadata());
			System.out.println(document.text());
		});

		// 从一个目录及其子目录中加载所有文档
		List<Document> documents5 = FileSystemDocumentLoader.loadDocumentsRecursively(directoryPath, new TextDocumentParser());
		documents5.forEach(document -> {
			System.out.println("=========================");
			System.out.println(document.metadata());
			System.out.println(document.text());
		});
	}

	/**
	 * 解析PDF
	 */
	@Test
	public void testParsePDF() {

		String directoryPath = "src/test/java/com/qiugq/xiaozhiai/knowledge";

		Document document = FileSystemDocumentLoader
				.loadDocument(directoryPath + "/医院信息.pdf", new ApachePdfBoxDocumentParser());
		System.out.println(document.metadata());
		System.out.println(document);
	}

	/**
	 * 解析微软文档
	 */
	@Test
	public void testParseMicrosoftDocuments() {

		String directoryPath = "src/test/java/com/qiugq/xiaozhiai/knowledge";

		Document document = FileSystemDocumentLoader
				.loadDocument(directoryPath + "/CTM仿射变换矩阵分析.docx", new ApachePoiDocumentParser());
		System.out.println(document.metadata());
		System.out.println(document);
	}

	/**
	 * 几乎解析任意文档
	 */
	@Test
	public void testParseAllDocuments() {

		String directoryPath = "src/test/java/com/qiugq/xiaozhiai/knowledge";

		Document document = FileSystemDocumentLoader
				.loadDocument(directoryPath + "/电子公文应用方案.ofd", new ApacheTikaDocumentParser());
		System.out.println(document.metadata());
		System.out.println(document);
	}

	/**
	 * 加载文档并存入向量数据库
	 */
	@Test
	public void testReadDocumentAndStore() {

		String directoryPath = "src/test/java/com/qiugq/xiaozhiai/knowledge";

		// 使用默认的文档解析器对文档进行解析(TextDocumentParser)
		Document document = FileSystemDocumentLoader.loadDocument(directoryPath + "/人工智能.md");

		//为了简单起见，我们暂时使用基于内存的向量存储
		InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

		// ingest
		// 1、分割文档：默认使用递归分割器，将文档分割为多个文本片段，每个片段包含不超过 300个token，并且有 30 个token的重叠部分保证连贯性
		// DocumentByParagraphSplitter(DocumentByLineSplitter(DocumentBySentenceSplitter(DocumentByWordSplitter)))
		// 2、文本向量化：使用一个LangChain4j内置的轻量化向量模型对每个文本片段进行向量化
		// 3、将原始文本和向量存储到向量数据库中(InMemoryEmbeddingStore)
		EmbeddingStoreIngestor.ingest(document, embeddingStore);
		// 查看向量数据库内容
		System.out.println(embeddingStore);
	}

	/**
	 * 文档分割
	 */
	@Test
	public void testDocumentSplitter() {

		String directoryPath = "src/test/java/com/qiugq/xiaozhiai/knowledge";

		// 使用FileSystemDocumentLoader读取指定目录下的知识库文档
		// 并使用默认的文档解析器对文档进行解析(TextDocumentParser)
		Document document = FileSystemDocumentLoader.loadDocument(directoryPath + "/人工智能.md");

		// 为了简单起见，我们暂时使用基于内存的向量存储
		InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

		// 自定义文档分割器
		// 按段落分割文档：每个片段包含不超过 300个token，并且有 30个token的重叠部分保证连贯性
		// 注意：当段落长度总和小于设定的最大长度时，就不会有重叠的必要。
		DocumentByParagraphSplitter documentSplitter = new DocumentByParagraphSplitter(
				300,
				30,
				// token分词器：按token计算
				new HuggingFaceTokenizer());
		// 按字符计算
		// DocumentByParagraphSplitter documentSplitter = new DocumentByParagraphSplitter(300, 30);

		EmbeddingStoreIngestor.builder()
				.embeddingStore(embeddingStore)
				.documentSplitter(documentSplitter)
				.build()
				.ingest(document);
	}

	/**
	 * token和token计算
	 */
	@Test
	public void testTokenCount() {

		String text = "这是一个示例文本，用于测试 token 长度的计算。";
		UserMessage userMessage = UserMessage.userMessage(text);

		// 计算 token 长度，使用默认的token分词器：按token计算
		// HuggingFaceTokenizer tokenizer = new HuggingFaceTokenizer();
		// 使用阿里通义千问分词器
		QwenTokenizer tokenizer = new QwenTokenizer(System.getenv("DASH_SCOPE_API_KEY"), "qwen-max");

		int count = tokenizer.estimateTokenCountInMessage(userMessage);
		System.out.println("token长度：" + count);
	}
}
