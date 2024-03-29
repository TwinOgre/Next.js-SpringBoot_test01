'use client'
import { useState, useEffect } from 'react'
interface articlesInterface{
  id: number,
  createdDate: string,
  modifiedDate: string,
  title: string,
  content: string
}

export default function Article() {
  const [articles, setArticles] = useState<articlesInterface[]>([]);
  useEffect(() => {
    (async() =>{
      const responce = await fetch("http://localhost:8090/api/v1/articles");
      const json = await responce.json();
      console.log(json)
      setArticles(json.data.articles);
    })();
  }, [])

  return (
    <div>
      <h1>Articles</h1>
      <ul>
        {articles.map(article => <li key={article.id}>{article.id} | {article.title}  | {article.content} | {article.createdDate} | {article.modifiedDate}</li> )}
      </ul>
    </div>
  );
}