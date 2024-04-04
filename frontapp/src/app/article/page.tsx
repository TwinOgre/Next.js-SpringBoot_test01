'use client'
import { useState, useEffect } from 'react'
import Link from "next/link";
interface articlesInterface {
  id: number,
  createdDate: string,
  modifiedDate: string,
  title: string,
  content: string
}

export default function Article() {
  const [articles, setArticles] = useState<articlesInterface[]>([]);
  useEffect(() => {
    const fetchArticles = async () => {
      const response = await fetch("http://localhost:8090/api/v1/articles");
      const json = await response.json();
      console.log(json);
      setArticles(json.data.articles);
    };

    fetchArticles();
  }, []);

  const handleDelete = async (article: articlesInterface, articleId: number) => {
    const fetchParam: string = "http://localhost:8090/api/v1/articles/" + articleId;
    const response = await fetch(fetchParam, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(article)
    });

    if (response.ok) {
      alert('게시물이 성공적으로 삭제되었습니다.');

    } else {
      alert('게시물 삭제에 실패했습니다.');
    }

  }
  return (
    <div>
      <h1>Articles</h1>
      <ul>
        {articles.map(article => <li key={article.id}><Link href={`/article/${article.id}`}>{article.id} 
        | {article.title}  | {article.content}</Link> | {article.createdDate} | {article.modifiedDate} 
        <button onClick={() => handleDelete(article, article.id)}>💥삭제</button></li>)}
      </ul>
    </div>
  );
}