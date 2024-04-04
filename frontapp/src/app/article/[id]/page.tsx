'use client'
import { useParams } from "next/navigation";
import { useState, useEffect } from 'react'
import Link from "next/link";

type articleInterface = {
  id: number,
  createdDate: string,
  modifiedDate: string,
  title: string,
  content: string
}
export default function Article() {
  const [article, setArticle] = useState<articleInterface>();
  const idParam = useParams();
  let requestArticle: string = "http://localhost:8090/api/v1/articles/" + idParam.id;
  useEffect(() => {
    (async () => {
      const responce = await fetch(requestArticle);
      const json = await responce.json();
      console.log(json)
      setArticle(json.data.article);
    })();
  }, [])

  return (
    <div>
      {article?.id} | {article?.title} | {article?.content} | {article?.createdDate} | {article?.modifiedDate}
      <br />
      <Link href={`/article/patch/${article?.id}`}>🅿수정</Link>
      <br />
    </div>
  );
}