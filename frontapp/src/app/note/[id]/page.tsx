'use client'
import { useParams } from "next/navigation";

export default function Note() {
    const idParam = useParams();
    return (
      <div>
        ✨노트 No. {idParam.id}✨
      </div>
    );
  }